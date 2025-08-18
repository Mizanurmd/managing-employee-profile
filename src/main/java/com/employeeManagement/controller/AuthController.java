package com.employeeManagement.controller;

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
    public ResponseEntity<ReqRest> regeister(@RequestBody ReqRest reg) {
        return ResponseEntity.ok(ourUserService.registerUser(reg));
    }

    @PostMapping("/login")
    public ResponseEntity<ReqRest> login(@RequestBody ReqRest login) {
        return ResponseEntity.ok(ourUserService.loginUser(login));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ReqRest> refreshToken(@RequestBody ReqRest req) {
        return ResponseEntity.ok(ourUserService.refreshToken(req));
    }

    @PostMapping("/logout")
    public ResponseEntity<ReqRest> logOut() {
        return ResponseEntity.ok(ourUserService.logoutUser());
    }


}
