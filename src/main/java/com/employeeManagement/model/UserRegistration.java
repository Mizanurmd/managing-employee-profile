package com.employeeManagement.model;

import com.employeeManagement.enums.RegistrationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="user_registration")
public class UserRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ORGANIZATION_NAME", length = 150, nullable = false)
    private String organizationName;

    @Column(name = "LICENSE_NUMBER", length = 100, nullable = false, unique = true)
    private String licenseNumber;

    @Column(name = "LICENSE_ISSUE_DATE")
    private LocalDate licenseIssueDate;

    @Column(name = "LICENSE_EXPIRY_DATE")
    private LocalDate licenseExpiryDate;

    @Column(name = "BSAA_MEMBERSHIP_ID", length = 100)
    private String bsaaMembershipId;

    @Column(name = "EMAIL_ADDRESS", length = 150, nullable = false, unique = true)
    private String emailAddress;

    @Column(name = "MOBILE_NUMBER", length = 20, nullable = false, unique = true)
    private String mobileNumber;

    @Column(name = "OFFICE_ADDRESS",  columnDefinition = "TEXT")
    private String officeAddress;

    @Column(name = "PORT_OF_OPERATION", length = 100, nullable = false)
    private String portOfOperation;

    @Column(name = "PASSWORD_HASH", length = 255, nullable = false)
    private String passwordHash;

    @Column(name = "PROFILE_PHOTO_PATH", length = 255)
    private String profilePhotoPath;

    @Column(name = "SCANNED_COPY_OF_LICENSE", length = 255, nullable = false)
    private String scannedCopyOfLicense;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 30)
    private RegistrationStatus status;

    @Column(name = "TRACKING_CODE", length = 100, nullable = false, unique = true)
    private String trackingCode;

    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;
}
