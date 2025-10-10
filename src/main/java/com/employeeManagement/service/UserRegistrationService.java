package com.employeeManagement.service;

import com.employeeManagement.dto.UserRegistrationCreateDto;
import com.employeeManagement.responseDto.UserRegistrationResponseDto;

import java.util.List;

public interface UserRegistrationService {
    UserRegistrationResponseDto create(UserRegistrationCreateDto dto);
    UserRegistrationResponseDto findById(Long id);
    UserRegistrationResponseDto findByTrackingCode(String trackingCode);
    List<UserRegistrationResponseDto> findAll();
    UserRegistrationResponseDto update(Long id, UserRegistrationCreateDto dto);
    void delete(Long id);
    // admin actions
    UserRegistrationResponseDto changeStatus(Long id, String status);
}
