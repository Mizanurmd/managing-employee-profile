package com.employeeManagement.exceptions.global;

import com.employeeManagement.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.webjars.NotFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponse<Object>>handleNotFound(NotFoundException e){
        log.error(e.getMessage(),e);
        BaseResponse response = new BaseResponse();
        response.setStatus(false);
        response.setMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(IllegalCallerException.class)
    public ResponseEntity<BaseResponse<Object>>handleIllegalArgument(IllegalCallerException e){
        log.error(e.getMessage(),e);
        BaseResponse response = new BaseResponse();
        response.setStatus(false);
        response.setMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>>handleValidation(MethodArgumentNotValidException e){
        String error = e.getBindingResult().getFieldError().getDefaultMessage();
        BaseResponse response = new BaseResponse();
        response.setStatus(false);
        response.setMessage(error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
