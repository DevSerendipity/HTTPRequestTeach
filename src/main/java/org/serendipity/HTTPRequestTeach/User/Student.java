package org.serendipity.HTTPRequestTeach.User;

import jakarta.persistence.*;

@Entity(name = "Student") @Table(name = "student") public class Student {
    @Id @SequenceGenerator(name = "student_sequence", sequenceName = "student_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")

    @Column(name = "id") private Long id;
    @Column(name = "email") private String email;
    @Column(name = "password") private String password;

    public Student(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Student() {

    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

}
