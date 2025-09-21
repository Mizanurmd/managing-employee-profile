package com.employeeManagement.repository;

import com.employeeManagement.model.TeacherBackup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherBackupRepository extends JpaRepository<TeacherBackup, Long> {
    Optional<TeacherBackup> findByTeacherId(String teacherId);
}
