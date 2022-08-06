package org.serendipity.HTTPRequestTeach.web;

import jakarta.transaction.Transactional;
import org.serendipity.HTTPRequestTeach.User.Student;
import org.serendipity.HTTPRequestTeach.User.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service @Transactional public class MyUserDetailsService implements UserDetailsService {

    @Autowired private StudentRepository studentRepository;

    @Override public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Student student = studentRepository.findStudentByEmail(email);
        if ( student == null ) {
            throw new UsernameNotFoundException("No user found with username: " + email);
        }

        return User.withUsername(student.getEmail()).password(student.getPassword()).authorities("USER").build();
    }

}
