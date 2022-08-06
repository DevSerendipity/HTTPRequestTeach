package org.serendipity.HTTPRequestTeach.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository public interface StudentRepository extends JpaRepository<Student, Long> {

    //@Query("SELECT c FROM Client c WHERE c.email = ?1") Student findClientByEmail(String email );
    @Query("SELECT c FROM Student c WHERE c.email = ?1") Student findStudentByEmail(String email );

    //Query(value = "SELECT MAX(id) FROM client", nativeQuery = true) int findClientById();
    @Query(value = "SELECT MAX(id) FROM student", nativeQuery = true) int findStudentById();
}
