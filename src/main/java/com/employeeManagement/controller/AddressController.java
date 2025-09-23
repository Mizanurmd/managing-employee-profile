package com.employeeManagement.controller;

import com.employeeManagement.dto.AddressDto;
import com.employeeManagement.responseDto.AddressResponseDto;
import com.employeeManagement.responseDto.ApiResponse;
import com.employeeManagement.service.AddressService;
import com.employeeManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/addresses")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AddressController {

    private final AddressService addressService;
    private final StudentService studentService;

    @Autowired
    public AddressController(AddressService addressService, StudentService studentService) {
        this.addressService = addressService;
        this.studentService = studentService;
    }

    // Save Address Handler
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<AddressResponseDto>> saveAddress(
            @RequestBody AddressDto dto) {
        ApiResponse response = new ApiResponse();
        try {
            response.setStatus("Success");
            response.setMessage("Address saved successfully");
            response.setMCode("200");
            response.setData(addressService.saveAddress(dto));
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setStatus("Error");
            response.setMessage("Address save failed");
            response.setMCode("500");
            response.setData(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


}
