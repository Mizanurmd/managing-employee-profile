package com.employeeManagement.repository;

import com.employeeManagement.model.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Long> {
    Optional<UserRegistration> findByEmailAddress(String email);
    Optional<UserRegistration> findByTrackingCode(String trackingCode);
    boolean existsByEmailAddress(String emailAddress);
    boolean existsByLicenseNumber(String licenseNumber);
    boolean existsByMobileNumber(String mobileNumber);
}
