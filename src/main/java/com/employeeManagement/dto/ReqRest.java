package com.employeeManagement.dto;

import com.employeeManagement.enums.Role;
import com.employeeManagement.model.OurUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReqRest {
    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private Role role;
    private OurUser user;
}
