package com.employeeManagement.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private String status;
    private String message;
    private String mCode;
    private T data;
}
