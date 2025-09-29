package com.employeeManagement.controller;

import com.employeeManagement.dto.AddressDto;
import com.employeeManagement.model.Address;
import com.employeeManagement.responseDto.AddressResponseDto;
import com.employeeManagement.responseDto.ApiResponse;
import com.employeeManagement.service.AddressService;
import com.employeeManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/addresses")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AddressController {

    private final AddressService addressService;


    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
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

    // get Address by id

    @GetMapping("/{id}")
    public ApiResponse<Address> getAddressById(@PathVariable("id") long id) {
        ApiResponse response = new ApiResponse();
        try {
            Optional<Address> address = addressService.addressById(id);
            response.setStatus("Success");
            response.setMessage("Address get successfully");
            response.setMCode("200");
            response.setData(address);
            return response;
        } catch (Exception e) {
            response.setStatus("Error");
            response.setMessage("Address get failed");
            response.setMCode("500");
            response.setData(e.getMessage());
            return response;
        }
    }

    // Update Address

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<AddressDto>> updateAddress(@PathVariable("id") long id, @RequestBody AddressDto dto) {
        ApiResponse response = new ApiResponse();
        try {
            Address updateAddress = addressService.updateAddress(id, dto);
            response.setStatus("Success");
            response.setMessage("Address updated successfully");
            response.setMCode("200");
            response.setData(updateAddress);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("Error");
            response.setMessage("Address update failed");
            response.setMCode("500");
            response.setData(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ApiResponse<Address> deleteAddressById(@PathVariable("id") long id) {
        ApiResponse response = new ApiResponse();
        try {
            Optional<Address> address = addressService.deleteById(id);
            response.setStatus("Success");
            response.setMessage("Address deleted successfully");
            response.setMCode("200");
            response.setData(address);
            return response;
        } catch (Exception e) {
            response.setStatus("Error");
            response.setMessage("Address deleted failed");
            response.setMCode("500");
            response.setData(e.getMessage());
            return response;
        }
    }


}
