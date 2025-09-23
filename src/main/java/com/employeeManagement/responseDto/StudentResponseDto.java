package com.employeeManagement.responseDto;

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
public class StudentResponseDto {
    private Long id;
    private String studentIdNo;
    private String firstName;
    private String lastName;
    private String fatherName;
    private String motherName;
    private String admissionNumber;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String email;
    private String phoneNumber;
    private StudentClass studentClass;
    private LocalDate admissionDate;
    private String section;
    private String rollNumber;
    private boolean active;
    private String profileImagePath;
    private List<AddressResponseDto> addresses;
}
