package com.employeeManagement.dto;

import com.employeeManagement.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
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
public class TeacherRequestDto {
    private Long id; // for update

    @NotBlank
    @Pattern(regexp = "^[A-Za-z. ]+$", message = "Name can only contain letters, dot, and spaces")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[0-9]{11}$", message = "Mobile must be 11 digits")
    private String mobile;

    @NotBlank
    @Email(message = "Invalid email format")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Pattern(regexp = "^(\\d{10}|\\d{17})$", message = "NID must be 10 or 17 digits")
    private String nid;


    @NotNull(message = "Date of Birth is required")
    @Past(message = "DOB must be before current date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    @Size(max = 200)
    private String presentAddress;

    @Size(max = 200)
    private String permanentAddress;

    @NotNull
    private Gender gender;

    private List<String> skills;

    @NotBlank
    private String highestEducation;


}
