package com.employeeManagement.service;

import com.employeeManagement.dto.AuthResponse;
import com.employeeManagement.dto.LoginRequest;
import com.employeeManagement.dto.ReqRest;
import com.employeeManagement.jwt.JWTUtil;
import com.employeeManagement.model.OurUser;
import com.employeeManagement.repository.OurUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class OurUserService {

    private final OurUserRepository usersRepo;

    private final JWTUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public OurUserService(OurUserRepository usersRepo, JWTUtil jwtUtil, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.usersRepo = usersRepo;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;

    }

    //================= user registration ================//
    public AuthResponse registerUser(ReqRest req) {
        AuthResponse res = new AuthResponse();
        try {
            //  check duplicate email
            if (usersRepo.existsByEmail(req.getEmail())) {
                res.setStatusCode(409);
                res.setError("Email is already in use!");
                return res;
            }

            OurUser ourUser = new OurUser();
            ourUser.setUsername(req.getUsername());
            ourUser.setEmail(req.getEmail());
            ourUser.setPassword(passwordEncoder.encode(req.getPassword()));
            ourUser.setRole(req.getRole());

            OurUser savedUser = usersRepo.save(ourUser);

            if (savedUser.getId() > 0) {
                res.setMessage("User registered successfully");
                res.setStatusCode(200);
            } else {
                res.setStatusCode(500);
                res.setError("User registration failed");
            }
        } catch (Exception e) {
            res.setStatusCode(500);
            res.setError(e.getMessage());
        }
        return res;
    }

    //======================== Login by Registered user =====================//
    public AuthResponse loginUser(LoginRequest logReq) {
        AuthResponse response = new AuthResponse();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(logReq.getEmail(), logReq.getPassword()));
            var user = usersRepo.findByEmail(logReq.getEmail()).orElseThrow();
            var jwt = jwtUtil.generateToken(user);
            var refreshToken = jwtUtil.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRole(user.getRole());
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully Logged In");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    //================= Refresh token ======================//
    public AuthResponse refreshToken(AuthResponse refreshTokenResponse) {
        AuthResponse response = new AuthResponse();
        try {
            String ourEmail = jwtUtil.extractUsername(refreshTokenResponse.getToken());
            OurUser users = usersRepo.findByEmail(ourEmail).orElseThrow();
            if (jwtUtil.isTokenValid(refreshTokenResponse.getToken(), users)) {
                var jwt = jwtUtil.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenResponse.getToken());
                response.setExpirationTime("24Hr");
                response.setMessage("Successfully Refreshed Token");
            }
            response.setStatusCode(200);
            return response;

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }

    //=========================== Logout ================================//
    public AuthResponse logoutUser() {
        AuthResponse resp = new AuthResponse();
        try {
            resp.setMessage("Successfully Logged Out");
            resp.setStatusCode(200);
        } catch (Exception e) {
            resp.setMessage("Error logging out");
            resp.setError(e.getMessage());
            resp.setStatusCode(500);

        }
        return resp;
    }


}
