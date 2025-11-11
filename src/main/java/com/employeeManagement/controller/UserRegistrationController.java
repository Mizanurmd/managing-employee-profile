package com.employeeManagement.controller;

import com.employeeManagement.dto.UserRegistrationCreateDto;
import com.employeeManagement.responseDto.UserRegistrationResponseDto;
import com.employeeManagement.service.UserRegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/registrations")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserRegistrationController {
    private final UserRegistrationService userRegistrationService;

    @Autowired
    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserRegistrationResponseDto>> getAllUserRegister() {
        return ResponseEntity.ok(userRegistrationService.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<UserRegistrationResponseDto> create(@Valid @RequestBody UserRegistrationCreateDto dto) {
        var created = userRegistrationService.create(dto);
        return ResponseEntity.created(URI.create("/api/v1/registrations/" + created.getId())).body(created);
    }

    // single Data get

    @GetMapping("/{id}")
    public ResponseEntity<UserRegistrationResponseDto> getUserRegister(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userRegistrationService.findById(id));
    }

    @GetMapping("/track/{code}")
    public ResponseEntity<UserRegistrationResponseDto> getTrackCode(@PathVariable("code") String code) {
        return ResponseEntity.ok(userRegistrationService.findByTrackingCode(code));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserRegistrationResponseDto> update(@PathVariable Long id, @Valid @RequestBody UserRegistrationCreateDto dto) {
        return ResponseEntity.ok(userRegistrationService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserRegistration(@PathVariable("id") Long id) {
        userRegistrationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // admin status change
    @PatchMapping("/{id}/status")
    public ResponseEntity<UserRegistrationResponseDto> changeStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(userRegistrationService.changeStatus(id, status));
    }

}
