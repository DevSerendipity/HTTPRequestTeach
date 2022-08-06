package org.serendipity.HTTPRequestTeach.User;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        if ( emailExists(student.getEmail()) ) {
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(student);
    }

    private boolean emailExists(String email) {
        return studentRepository.findStudentByEmail(email) != null;
    }

    public void deleteStudent(Long studentId) {
        boolean studentExists = studentRepository.existsById(studentId);
        if ( !studentExists ) {
            throw new IllegalStateException("student with id:" + studentId + " does not exist.");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional public void updateStudent(Long studentId, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("student with id:" + studentId + " does not exist"));

        if ( email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email) ) {
            if ( emailExists(student.getEmail()) ) {
                throw new IllegalStateException("email taken");
            }
            student.setEmail(email);
        }
    }
}
