package com.employeeManagement.repository;

import com.employeeManagement.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long>, JpaSpecificationExecutor<Teacher> {
    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);

    boolean existsByNid(String nid);

    // soft delete method
    Optional<Teacher> findByTeacherId(String teacherId);

    List<Teacher> findAllByIsDeletedFalse();
}
