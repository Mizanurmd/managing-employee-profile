package com.employeeManagement.repository;

import com.employeeManagement.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long>, JpaSpecificationExecutor<Teacher> {
    boolean existsByEmail(String email);
    boolean existsByMobile(String mobile);
    boolean existsByNid(String nid);
}
