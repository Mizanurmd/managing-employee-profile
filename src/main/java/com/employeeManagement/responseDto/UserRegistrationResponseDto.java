package com.employeeManagement.responseDto;

import com.employeeManagement.enums.RegistrationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationResponseDto {
    private Long id;
    private String organizationName;
    private String licenseNumber;
    private LocalDate licenseIssueDate;
    private LocalDate licenseExpiryDate;
    private String bsaaMembershipId;
    private String emailAddress;
    private String mobileNumber;
    private String officeAddress;
    private String portOfOperation;
    private String profilePhotoPath;
    private String scannedCopyOfLicense;
    private RegistrationStatus status;
    private String trackingCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
