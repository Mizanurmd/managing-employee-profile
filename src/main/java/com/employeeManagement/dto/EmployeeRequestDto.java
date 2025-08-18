package com.employeeManagement.dto;


import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequestDto {
    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[a-zA-Z. ]+$", message = "Name can only contain letters, dots, and spaces")
    private String name;

    @NotBlank(message = "Mobile is required")
    @Pattern(regexp = "^[0-9]{11}$", message = "Mobile must be 11 digits")
    private String mobile;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "NID is required")
    @Pattern(regexp = "^[0-9]{10}|[0-9]{17}$", message = "NID must be 10 or 17 digits")
    private String nid;

    @NotNull(message = "Date of Birth is required")
    @Past(message = "Date of Birth must be before today")
    private LocalDate dateOfBirth;

    @Size(max = 200, message = "Present Address max 200 characters")
    private String presentAddress;

    @Size(max = 200, message = "Permanent Address max 200 characters")
    private String permanentAddress;

    @NotBlank(message = "Gender is required")
    private String gender;

    private String skills;

    @NotBlank(message = "Highest education is required")
    private String highestEducation;

    // for photo
    private MultipartFile profileImage;
    private String imageType;
    private String imageName;
    private Long imageSize;
}
