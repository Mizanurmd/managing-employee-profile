package com.employeeManagement.controller;

import com.employeeManagement.responseDto.ApiResponse;
import com.employeeManagement.dto.EmployeeRequestDto;
import com.employeeManagement.model.Employee;
import com.employeeManagement.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/employees")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeController {
    private final EmployeeService employeeService;


    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;

    }

    // Employee Add Handler
    @PostMapping("/save")
    public ResponseEntity<Employee> addEmployee(@Valid @ModelAttribute EmployeeRequestDto employeeRequestDto) throws IOException {
        Employee saveEmployee = employeeService.createEmployee(employeeRequestDto);
        return new ResponseEntity<>(saveEmployee, HttpStatus.CREATED);
    }

    // Employee update Handler
    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") String id, @Valid @ModelAttribute EmployeeRequestDto employeeRequestDto) throws IOException {
        Employee updateEmployee = employeeService.updateEmployee(id, employeeRequestDto);
        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);

    }

    // Employee single Data retrieve handler
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") String id) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(employee);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Employee delete handler

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable("id") String id) {
        employeeService.deleteEmployee(id);
        return "Employee deleted successfully";
    }

    // Search Employees with Filters handler
    @GetMapping("/search")
    public ResponseEntity<Page<Employee>> searchEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String mobile,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String subject,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Employee> employees = employeeService.searchEmployees(name, mobile, email, subject, page, size);
        return ResponseEntity.ok(employees);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Page<Employee> employeePage = employeeService.getAllEmployees(page, size, sortBy, sortDir);

        Map<String, Object> response = new HashMap<>();
        response.put("employees", employeePage.getContent());
        response.put("currentPage", employeePage.getNumber());
        response.put("totalItems", employeePage.getTotalElements());
        response.put("totalPages", employeePage.getTotalPages());
        response.put("sortBy", sortBy);
        response.put("sortDir", sortDir);

        return ResponseEntity.ok(response);
    }

    // Added custom search handler
    @GetMapping("/searchBy")
    public ResponseEntity<ApiResponse<List<Employee>>> getAllEmployeesByCriteria(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String mobile,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
    ) {
        ApiResponse<List<Employee>> apiResponse = new ApiResponse<>();
        try {
            List<Employee> employeesSearchList = employeeService.searchEmployeeByCriteriaWithDate(id, email, mobile, fromDate, toDate);
            apiResponse.setStatus("Success");
            apiResponse.setMessage("Employee search list successfully");
            apiResponse.setMCode("200");
            apiResponse.setData(employeesSearchList);
            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            apiResponse.setStatus("Error");
            apiResponse.setMessage(e.getMessage());
            apiResponse.setMCode("500");
            apiResponse.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }


}
