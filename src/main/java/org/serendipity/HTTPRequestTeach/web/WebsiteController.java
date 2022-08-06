package org.serendipity.HTTPRequestTeach.web;

import jakarta.servlet.http.HttpServletResponse;
import org.serendipity.HTTPRequestTeach.User.Student;
import org.serendipity.HTTPRequestTeach.User.StudentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller public class WebsiteController {

    private final StudentService studentService;
    @Value("${file.upload-dir}") private String fileDirectory;

    public WebsiteController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/") public String homePage() {
        return "/homePage.html";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET) public String login() {
        return "/login.html";
    }
    @RequestMapping(value = "/deletePage", method = RequestMethod.GET) public String delete() {
        return "/deletePage.html";
    }
    @RequestMapping(value = "/getPage", method = RequestMethod.GET) public String getPage() {
        return "/getPage.html";
    }
    @RequestMapping(value = "/headPage", method = RequestMethod.GET) public String headPage() {
        return "/headPage.html";
    }
    @RequestMapping(value = "/patchPage", method = RequestMethod.GET) public String patchPage() {
        return "/patchPage.html";
    }
    @RequestMapping(value = "/postPage", method = RequestMethod.GET) public String postPage() {
        return "/postPage.html";
    }
    @RequestMapping(value = "/putPage", method = RequestMethod.GET) public String putPage() {
        return "/putPage.html";
    }

    @GetMapping("/file") public String file() {
        return "/filePicker.html";
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public void fileUpload(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        String fileName = file.getOriginalFilename();
        writeFileToDirectory(file, fileName);
        /*AESHandler.startCryptography(fileDirectory + fileName);
        response.sendRedirect("/download/" + AESHandler.getTrueFileName());*/
    }

    private void writeFileToDirectory(MultipartFile file, String fileName) {
        try ( FileOutputStream fos = new FileOutputStream(fileDirectory + fileName); ) {
            fos.write(file.getBytes());
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Object> downloadFileFromLocal(@PathVariable String fileName) {
        Path path = Paths.get(fileDirectory + fileName);
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch ( MalformedURLException e ) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/pdf"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET) public String getStudent(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        model.addAttribute("student", new Student());
        return "register.html";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String saveStudent(Model model, @ModelAttribute Student student) {
        Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder();
        student.setPassword(argon2PasswordEncoder.encode(student.getPassword()));
        System.out.println(student.getPassword());
        model.addAttribute("password", student.getPassword());
        studentService.addNewStudent(student);
        return "redirect:/register";
    }

}
