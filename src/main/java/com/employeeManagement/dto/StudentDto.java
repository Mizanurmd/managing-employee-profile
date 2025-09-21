package com.employeeManagement.dto;

import com.employeeManagement.enums.Gender;
import com.employeeManagement.enums.StudentClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDto {
    private String studentId;
    private String firstName;
    private String lastName;
    private String admissionNumber;
    private LocalDate dateOfBirth;
    private Gender gender;

    // Skills as list of strings for frontend
    private List<String> skills;

    private String email;
    private String phoneNumber;
    private String presentAddress;
    private String permanentAddress;

    private StudentClass studentClass;
    private LocalDate admissionDate;
    private String section;
    private String rollNumber;
    private String profileImagePath;
    private boolean active;
    private LocalDate deletedAt;
}
