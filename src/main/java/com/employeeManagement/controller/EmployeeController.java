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

@RestController
@RequestMapping("/api/v1/employees")
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

    @GetMapping
    public ResponseEntity<Page<Employee>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return new ResponseEntity<>(employeeService.getAllEmployees(page, size, sortBy, direction), HttpStatus.OK);

    }

    @GetMapping("/search")
    public Page<Employee> searchEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String mobile,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String skills,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll((root, query, cb) -> {
            var predicates = cb.conjunction();

            if (name != null) {
                predicates = cb.and(predicates, cb.like(root.get("name"), "%" + name + "%"));
            }
            if (mobile != null) {
                predicates = cb.and(predicates, cb.equal(root.get("mobile"), mobile));
            }
            if (email != null) {
                predicates = cb.and(predicates, cb.equal(root.get("email"), email));
            }
            if (skills != null) {
                predicates = cb.and(predicates, cb.like(root.get("skills"), "%" + skills + "%"));
            }

            return predicates;
        }, pageable);
    }


}
