package org.serendipity.HTTPRequestTeach.web;

import org.serendipity.HTTPRequestTeach.User.Student;
import org.serendipity.HTTPRequestTeach.User.StudentService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller public class WebsiteController {

    private final StudentService studentService;

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
