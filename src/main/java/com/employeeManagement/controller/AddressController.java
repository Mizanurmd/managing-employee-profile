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
    public ResponseEntity<ApiResponse<Address>> saveAddress(@RequestBody AddressDto dto) {
        ApiResponse<Address> response = new ApiResponse<>();
        try {
            Address save = addressService.saveAddress(dto);
            response.setStatus("Success");
            response.setMessage("Address saved successfully");
            response.setMCode("200");
            response.setData(save);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setStatus("Error");
            response.setMessage("Address save failed");
            response.setMCode("500");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // get Address by id

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Address>> getAddressById(@PathVariable("id") long id) {
        ApiResponse<Address> response = new ApiResponse<>();
        try {
            Optional<Address> addressOptional = addressService.addressById(id);
            if (addressOptional.isPresent()) {
                response.setStatus("Success");
                response.setMessage("Address retrieved successfully");
                response.setMCode("200");
                response.setData(addressOptional.get());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setStatus("Error");
                response.setMessage("Address not found");
                response.setMCode("404");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setStatus("Error");
            response.setMessage("Failed to retrieve address");
            response.setMCode("500");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Update Address

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Address>> updateAddress(@PathVariable("id") long id,
                                                              @RequestBody AddressDto dto) {
        ApiResponse<Address> response = new ApiResponse<>();
        try {
            Address updatedAddress = addressService.updateAddress(id, dto);
            response.setStatus("Success");
            response.setMessage("Address updated successfully");
            response.setMCode("200");
            response.setData(updatedAddress);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("Error");
            response.setMessage("Address update failed");
            response.setMCode("500");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Address>> deleteAddressById(@PathVariable("id") long id) {
        ApiResponse<Address> response = new ApiResponse<>();
        try {
            Optional<Address> deletedAddress = addressService.deleteById(id);
            if (deletedAddress.isPresent()) {
                response.setStatus("Success");
                response.setMessage("Address deleted successfully");
                response.setMCode("200");
                response.setData(deletedAddress.get());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setStatus("Error");
                response.setMessage("Address not found");
                response.setMCode("404");
                response.setData(null);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setStatus("Error");
            response.setMessage("Address delete failed");
            response.setMCode("500");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
