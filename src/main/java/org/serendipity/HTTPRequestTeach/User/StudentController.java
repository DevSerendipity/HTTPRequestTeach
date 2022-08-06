package org.serendipity.HTTPRequestTeach.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping(path = "api/v1/student") public class StudentController {

    private final StudentService studentService;

    @Autowired public StudentController(StudentService userService) {
        this.studentService = userService;
    }

    @GetMapping public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping public void newStudent(@RequestBody Student student) {
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "{studentId}") public void deleteStudent(@PathVariable("studentId") Long id) {
        studentService.deleteStudent(id);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable("studentId") Long studentId, @RequestParam(required = false) String email) {
        studentService.updateStudent(studentId, email);
    }
}
