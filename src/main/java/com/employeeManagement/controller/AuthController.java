package com.employeeManagement.controller;

import com.employeeManagement.dto.AuthResponse;
import com.employeeManagement.dto.LoginRequest;
import com.employeeManagement.dto.ReqRest;
import com.employeeManagement.service.OurUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    private final OurUserService ourUserService;

    @Autowired
    public AuthController(OurUserService ourUserService) {
        this.ourUserService = ourUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody ReqRest reg) {
        return ResponseEntity.ok(ourUserService.registerUser(reg));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest login) {
        return ResponseEntity.ok(ourUserService.loginUser(login));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody AuthResponse authResponse) {
        return ResponseEntity.ok(ourUserService.refreshToken(authResponse));
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logOut() {
        return ResponseEntity.ok(ourUserService.logoutUser());
    }


}
