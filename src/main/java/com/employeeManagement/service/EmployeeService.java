package com.employeeManagement.service;

import com.employeeManagement.dto.EmployeeRequestDto;
import com.employeeManagement.model.Employee;
import org.springframework.data.domain.Page;

import java.io.IOException;


public interface EmployeeService {
    Employee createEmployee(EmployeeRequestDto employeeRequestDto) throws IOException;

    Employee updateEmployee(String id, EmployeeRequestDto updatedEmployeeRequestDto) throws IOException;

    void deleteEmployee(String id);

    Employee getEmployeeById(String id);

    Page<Employee> getAllEmployees(int page, int size, String sortBy, String direction);
}
