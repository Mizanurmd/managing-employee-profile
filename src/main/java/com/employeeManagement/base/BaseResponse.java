package com.employeeManagement.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean status;
    private String message;
    private String messageBn;
    private String code;
    private Object data;
}
