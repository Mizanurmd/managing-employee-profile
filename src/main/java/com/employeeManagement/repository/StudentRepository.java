package com.employeeManagement.repository;

import com.employeeManagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT MAX(t.studentIdNo) FROM Student t")
    String findMaxStudentId();
}
