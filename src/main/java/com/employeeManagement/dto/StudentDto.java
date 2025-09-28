package com.employeeManagement.dto;

import com.employeeManagement.enums.Gender;
import com.employeeManagement.enums.StudentClass;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDto {
    private Long studentId;
    private String firstName;
    private String lastName;
    private String fatherName;
    private String motherName;
    private String admissionNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    private Gender gender;
    private String email;
    private String phoneNumber;
    private StudentClass studentClass;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate admissionDate;

    private String section;
    private String rollNumber;
    private boolean active;

    private List<AddressDto> addresses;
}
