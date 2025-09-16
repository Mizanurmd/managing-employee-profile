package com.employeeManagement.serviceImpl;

import com.employeeManagement.dto.EmployeeRequestDto;
import com.employeeManagement.model.Employee;
import com.employeeManagement.repository.EmployeeRepository;
import com.employeeManagement.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import jakarta.persistence.criteria.Predicate;

import java.time.LocalDate;
import java.util.List;
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
    public Page<Employee> searchEmployees(String name, String mobile, String email, String subject, int page, int size) {
        Specification<Employee> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (name != null && !name.isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (mobile != null && !mobile.isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("mobile"), "%" + mobile + "%"));
            }
            if (email != null && !email.isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
            }
            if (subject != null && !subject.isEmpty()) {
                // Assuming subject is stored in "skills" or "highestEducation"
                predicate = cb.and(predicate,
                        cb.or(
                                cb.like(cb.lower(root.get("skills")), "%" + subject.toLowerCase() + "%"),
                                cb.like(cb.lower(root.get("highestEducation")), "%" + subject.toLowerCase() + "%")
                        ));
            }

            return predicate;
        };

        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(spec, pageable);
    }

    @Override
    public Page<Employee> getAllEmployees(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return employeeRepository.findAll(pageable);
    }


    @Transactional
    @Override
    public List<Employee> searchEmployeeByCriteriaWithDate(String id, String email, String mobile, LocalDate fromDate, LocalDate toDate) {
        // data validation logic
        if ((fromDate != null && toDate == null) || (fromDate == null && toDate != null)) {
            throw new IllegalArgumentException("Both fromData and toDate must be provided together");

        }
        if (fromDate != null && toDate != null) {
            return employeeRepository.searchEmployeeByCriteriaWithDate(id, email, mobile, fromDate, toDate);
        } else {
            return employeeRepository.searchEmployeeByCriteriaWithoutDate(id, email, mobile);
        }

    }


}



