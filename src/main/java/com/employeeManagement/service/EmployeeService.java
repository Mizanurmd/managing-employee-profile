package com.employeeManagement.service;

import com.employeeManagement.dto.EmployeeRequestDto;
import com.employeeManagement.model.Employee;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


public interface EmployeeService {
    Employee createEmployee(EmployeeRequestDto employeeRequestDto) throws IOException;

    Employee updateEmployee(String id, EmployeeRequestDto updatedEmployeeRequestDto) throws IOException;

    void deleteEmployee(String id);

    Employee getEmployeeById(String id);

    Page<Employee> searchEmployees(String name, String mobile, String email, String subject, int page, int size);

    Page<Employee> getAllEmployees(int page, int size, String sortBy, String sortDir);

    List<Employee> searchEmployeeByCriteriaWithDate(String id, String email, String mobile, LocalDate formDate, LocalDate toDate);


}
