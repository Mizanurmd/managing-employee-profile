package com.employeeManagement.serviceImpl;

import com.employeeManagement.dto.EmployeeRequestDto;
import com.employeeManagement.model.Employee;
import com.employeeManagement.repository.EmployeeRepository;
import com.employeeManagement.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Generate 8-digit employeeId
    private String generateEmployeeId() {
        long count = employeeRepository.count() + 1;
        return String.format("%08d", count);
    }

    @Override
    public Employee createEmployee(EmployeeRequestDto employeeRequestDto) throws IOException {
        Employee employee = new Employee();

        // Basic Info
        employee.setId(generateEmployeeId());
        employee.setName(employeeRequestDto.getName());
        employee.setMobile(employeeRequestDto.getMobile());
        employee.setEmail(employeeRequestDto.getEmail());
        employee.setNid(employeeRequestDto.getNid()); // real NID comes from request
        employee.setDateOfBirth(employeeRequestDto.getDateOfBirth());
        employee.setPresentAddress(employeeRequestDto.getPresentAddress());
        employee.setPermanentAddress(employeeRequestDto.getPermanentAddress());
        employee.setGender(employeeRequestDto.getGender());
        employee.setSkills(employeeRequestDto.getSkills());
        employee.setHighestEducation(employeeRequestDto.getHighestEducation());

        // Photo Handling
        if (employeeRequestDto.getProfileImage() != null && !employeeRequestDto.getProfileImage().isEmpty()) {
            MultipartFile image = employeeRequestDto.getProfileImage();
            employee.setProfileImage(image.getBytes());
            employee.setImageType(image.getContentType());
            employee.setImageName(image.getOriginalFilename());
            employee.setImageSize(image.getSize());
        }

        // Save to DB
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(String id, EmployeeRequestDto updatedEmployeeRequestDto) throws IOException {
        Optional<Employee> empOptional = employeeRepository.findById(id);
        if (empOptional.isPresent()) {
            Employee employee = empOptional.get();

            // Basic Info (do NOT change the ID)
            employee.setName(updatedEmployeeRequestDto.getName());
            employee.setMobile(updatedEmployeeRequestDto.getMobile());
            employee.setEmail(updatedEmployeeRequestDto.getEmail());
            employee.setNid(updatedEmployeeRequestDto.getNid()); // real NID comes from request
            employee.setDateOfBirth(updatedEmployeeRequestDto.getDateOfBirth());
            employee.setPresentAddress(updatedEmployeeRequestDto.getPresentAddress());
            employee.setPermanentAddress(updatedEmployeeRequestDto.getPermanentAddress());
            employee.setGender(updatedEmployeeRequestDto.getGender());
            employee.setSkills(updatedEmployeeRequestDto.getSkills());
            employee.setHighestEducation(updatedEmployeeRequestDto.getHighestEducation());

            // Photo Handling
            if (updatedEmployeeRequestDto.getProfileImage() != null && !updatedEmployeeRequestDto.getProfileImage().isEmpty()) {
                MultipartFile image = updatedEmployeeRequestDto.getProfileImage();
                employee.setProfileImage(image.getBytes());
                employee.setImageType(image.getContentType());
                employee.setImageName(image.getOriginalFilename());
                employee.setImageSize(image.getSize());
            }

            return employeeRepository.save(employee);
        } else {
            throw new RuntimeException("Employee id with :: " + id + " not found");
        }
    }


    @Override
    public void deleteEmployee(String id) {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employee not found");
        }
        employeeRepository.deleteById(id);

    }

    @Override
    public Employee getEmployeeById(String id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee id with :: " + id + " not found"));

    }

    @Override
    public Page<Employee> getAllEmployees(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return employeeRepository.findAll(pageable);
    }
}
