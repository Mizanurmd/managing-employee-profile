package com.employeeManagement.service;

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

    //=================Register user ================//
    public ReqRest registerUser(ReqRest req) {
        ReqRest resp = new ReqRest();
        try {

            OurUser ourUser = new OurUser();
            ourUser.setUsername(req.getName());
            ourUser.setEmail(req.getEmail());
            ourUser.setPassword(passwordEncoder.encode(req.getPassword()));
            ourUser.setRole(req.getRole());
            OurUser saveReg = usersRepo.save(ourUser);
            if (saveReg.getId() > 0) {
                resp.setUser(saveReg);
                resp.setMessage("User registered successfully");
                resp.setStatusCode(200);

            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    //======================== Login by Register user =====================//
    public ReqRest loginUser(ReqRest req) {
        ReqRest resp = new ReqRest();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
            var user = usersRepo.findByEmail(req.getEmail()).orElseThrow();
            var jwt = jwtUtil.generateToken(user);
            var refreshToken = jwtUtil.generateRefreshToken(new HashMap<>(), user);
            resp.setStatusCode(200);
            resp.setToken(jwt);
            resp.setRole(user.getRole());
            resp.setRefreshToken(refreshToken);
            resp.setExpirationTime("24Hrs");
            resp.setMessage("Successfully Logged In");
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    //================= Refresh token ======================//
    public ReqRest refreshToken(ReqRest refreshTokenReqrest) {
        ReqRest response = new ReqRest();
        try {
            String ourEmail = jwtUtil.extractUsername(refreshTokenReqrest.getToken());
            OurUser users = usersRepo.findByEmail(ourEmail).orElseThrow();
            if (jwtUtil.isTokenValid(refreshTokenReqrest.getToken(), users)) {
                var jwt = jwtUtil.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenReqrest.getToken());
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
    public ReqRest logoutUser() {
        ReqRest resp = new ReqRest();
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
