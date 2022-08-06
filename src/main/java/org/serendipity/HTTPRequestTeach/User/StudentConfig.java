package org.serendipity.HTTPRequestTeach.User;

import org.serendipity.HTTPRequestTeach.File.FileManagement;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.util.List;

@Configuration public class StudentConfig {
    private final FileManagement fileManagement = new FileManagement();

    @Bean CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        int getLastStudentById = studentRepository.findStudentById();
        fileManagement.addAllEmails();
        System.out.println("START");
        return args -> {
            Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder();
            for ( int i = 0; i < 2; i++ ) {
                studentRepository.saveAll(List.of(new Student(fileManagement.getEmails().get(getLastStudentById + i),
                                                              argon2PasswordEncoder.encode(fileManagement.getEmails()
                                                                                                 .get((getLastStudentById
                                                                                                         + i))))));
            }
        };
    }
}
