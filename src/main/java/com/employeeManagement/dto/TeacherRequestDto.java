package com.employeeManagement.dto;

import com.employeeManagement.enums.Gender;
import jakarta.validation.constraints.*;
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
public class TeacherRequestDto {
    private Long id; // for update

    @NotBlank
    @Pattern(regexp = "^[A-Za-z. ]+$")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[0-9]{11}$")
    private String mobile;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^(\\d{10}|\\d{17})$")
    private String nid;

    @NotNull
    @Past
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
