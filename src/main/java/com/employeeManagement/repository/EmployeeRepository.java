package com.employeeManagement.repository;

import com.employeeManagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>, JpaSpecificationExecutor<Employee> {
    // With date
    @Query("""
                SELECT e FROM Employee e
                WHERE (:id IS NULL OR e.id = :id)
                  AND (:email IS NULL OR e.email = :email)
                  AND (:mobile IS NULL OR e.mobile = :mobile)
                  AND e.dateOfBirth BETWEEN :fromDate AND :toDate
            """)
    List<Employee> searchEmployeeByCriteriaWithDate(
            @Param("id") String id,
            @Param("email") String email,
            @Param("mobile") String mobile,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );

    // Without date
    @Query("""
                SELECT e FROM Employee e
                WHERE (:id IS NULL OR e.id = :id)
                  AND (:email IS NULL OR e.email = :email)
                  AND (:mobile IS NULL OR e.mobile = :mobile)
            """)
    List<Employee> searchEmployeeByCriteriaWithoutDate(
            @Param("id") String id,
            @Param("email") String email,
            @Param("mobile") String mobile
    );


}
