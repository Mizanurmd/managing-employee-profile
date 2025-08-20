package com.employeeManagement.controller;

import com.employeeManagement.dto.EmployeeRequestDto;
import com.employeeManagement.model.Employee;
import com.employeeManagement.repository.EmployeeRepository;
import com.employeeManagement.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
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

    // Get all Employee List handler

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> allEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
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


}
