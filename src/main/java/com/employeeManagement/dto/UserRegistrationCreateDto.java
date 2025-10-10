package com.employeeManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationCreateDto {
    @NotBlank(message = "Organization name is required")
    private String organizationName;


    @NotBlank(message = "License number is required")
    private String licenseNumber;


    // YYYY-MM-DD
    private LocalDate licenseIssueDate;
    private LocalDate licenseExpiryDate;


    private String bsaaMembershipId;


    @NotBlank
    @Email(message = "Must be a valid email")
    private String emailAddress;


    @NotBlank
    @Pattern(regexp = "^01[3-9]\\d{8}$", message = "Must be a valid Bangladeshi mobile number")
    private String mobileNumber;


    private String officeAddress;


    @NotBlank
    private String portOfOperation;


    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password; // plain password on DTO — hash in entity


    private String profilePhotoPath;


    @NotBlank
    private String scannedCopyOfLicense;
}
