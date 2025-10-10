package com.employeeManagement.serviceImpl;

import com.employeeManagement.dto.UserRegistrationCreateDto;
import com.employeeManagement.enums.RegistrationStatus;
import com.employeeManagement.model.UserRegistration;
import com.employeeManagement.repository.UserRegistrationRepository;
import com.employeeManagement.responseDto.UserRegistrationResponseDto;
import com.employeeManagement.service.UserRegistrationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {
    private final UserRegistrationRepository userRegistrationRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRegistrationServiceImpl(UserRegistrationRepository userRegistrationRepository, PasswordEncoder passwordEncoder) {
        this.userRegistrationRepository = userRegistrationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserRegistrationResponseDto create(UserRegistrationCreateDto dto) {
        // basic uniqueness checks
        if (userRegistrationRepository.existsByEmailAddress(dto.getEmailAddress()))
            throw new IllegalArgumentException("Email already registered");
        if (userRegistrationRepository.existsByLicenseNumber(dto.getLicenseNumber()))
            throw new IllegalArgumentException("License number already registered");
        if (userRegistrationRepository.existsByMobileNumber(dto.getMobileNumber()))
            throw new IllegalArgumentException("Mobile number already registered");


        String hashed = passwordEncoder.encode(dto.getPassword());
        String tracking = generateTrackingCode();
        UserRegistration entity = toEntity(dto, hashed, tracking);
        UserRegistration saved = userRegistrationRepository.save(entity);
        return toDto(saved);
    }

    @Override
    public UserRegistrationResponseDto findById(Long id) {
        return userRegistrationRepository.findById(id).map((u) -> toDto(u)).orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    @Override
    public UserRegistrationResponseDto findByTrackingCode(String trackingCode) {
        return userRegistrationRepository.findByTrackingCode(trackingCode).map((u) -> toDto(u)).orElseThrow(() -> new RuntimeException("trackingCode Not Found"));
    }

    @Override
    public List<UserRegistrationResponseDto> findAll() {
        return userRegistrationRepository.findAll().stream().map((u) -> toDto(u)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserRegistrationResponseDto update(Long id, UserRegistrationCreateDto dto) {
        UserRegistration ex = userRegistrationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found"));
        // update allowed fields
        ex.setOrganizationName(dto.getOrganizationName());
        ex.setLicenseNumber(dto.getLicenseNumber());
        ex.setLicenseIssueDate(dto.getLicenseIssueDate());
        ex.setLicenseExpiryDate(dto.getLicenseExpiryDate());
        ex.setBsaaMembershipId(dto.getBsaaMembershipId());
        ex.setEmailAddress(dto.getEmailAddress());
        ex.setMobileNumber(dto.getMobileNumber());
        ex.setOfficeAddress(dto.getOfficeAddress());
        ex.setPortOfOperation(dto.getPortOfOperation());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            ex.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }
        ex.setProfilePhotoPath(dto.getProfilePhotoPath());
        ex.setScannedCopyOfLicense(dto.getScannedCopyOfLicense());
        UserRegistration saved = userRegistrationRepository.save(ex);
        return toDto(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRegistrationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserRegistrationResponseDto changeStatus(Long id, String status) {
        UserRegistration ex = userRegistrationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found"));
        ex.setStatus(Enum.valueOf(RegistrationStatus.class, status.toUpperCase()));
        return toDto(userRegistrationRepository.save(ex));
    }

    // ===================== Generate Tracker number =========================//
    private String generateTrackingCode() {
        // Example: UUID-based short code — replace with business rule if needed
        return "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    //============= Mapper utility (manual)====================//
    public static UserRegistration toEntity(UserRegistrationCreateDto dto, String hashedPassword, String trackingCode) {
        return UserRegistration.builder().organizationName(dto.getOrganizationName()).licenseNumber(dto.getLicenseNumber()).licenseIssueDate(dto.getLicenseIssueDate()).licenseExpiryDate(dto.getLicenseExpiryDate()).bsaaMembershipId(dto.getBsaaMembershipId()).emailAddress(dto.getEmailAddress()).mobileNumber(dto.getMobileNumber()).officeAddress(dto.getOfficeAddress()).portOfOperation(dto.getPortOfOperation()).passwordHash(hashedPassword).profilePhotoPath(dto.getProfilePhotoPath()).scannedCopyOfLicense(dto.getScannedCopyOfLicense()).status(RegistrationStatus.PENDING).trackingCode(trackingCode).build();
    }

    public static UserRegistrationResponseDto toDto(UserRegistration e) {
        return UserRegistrationResponseDto.builder().id(e.getId()).organizationName(e.getOrganizationName()).licenseNumber(e.getLicenseNumber()).licenseIssueDate(e.getLicenseIssueDate()).licenseExpiryDate(e.getLicenseExpiryDate()).bsaaMembershipId(e.getBsaaMembershipId()).emailAddress(e.getEmailAddress()).mobileNumber(e.getMobileNumber()).officeAddress(e.getOfficeAddress()).portOfOperation(e.getPortOfOperation()).profilePhotoPath(e.getProfilePhotoPath()).scannedCopyOfLicense(e.getScannedCopyOfLicense()).status(e.getStatus()).trackingCode(e.getTrackingCode()).createdAt(e.getCreatedAt()).updatedAt(e.getUpdatedAt()).build();
    }

}
