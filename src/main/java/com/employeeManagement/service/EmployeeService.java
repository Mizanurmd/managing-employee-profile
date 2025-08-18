package com.employeeManagement.service;

import com.employeeManagement.dto.EmployeeRequestDto;
import com.employeeManagement.model.Employee;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;


public interface EmployeeService {
    Employee createEmployee(EmployeeRequestDto employeeRequestDto) throws IOException;

    Employee updateEmployee(String id, EmployeeRequestDto updatedEmployeeRequestDto) throws IOException;

    void deleteEmployee(String id);

    Employee getEmployeeById(String id);

    Page<Employee> searchEmployees(String name, String mobile, String email, String subject, int page, int size);
}
