package com.employeeManagement.controller;

import com.employeeManagement.dto.FeeReceiptDto;
import com.employeeManagement.model.FeeReceipt;
import com.employeeManagement.responseDto.ApiResponse;
import com.employeeManagement.service.FeeReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/fee-receipts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FeeReceiptController {
    private final FeeReceiptService feeReceiptService;

    @Autowired
    public FeeReceiptController(FeeReceiptService feeReceiptService) {
        this.feeReceiptService = feeReceiptService;
    }

    @PostMapping("/save")
    public ApiResponse<FeeReceipt> createFeeReceipt(@RequestBody FeeReceiptDto feeReceiptDto) {
        ApiResponse<FeeReceipt> response = new ApiResponse<>();
        try {
            FeeReceipt savedFeeReceipt = feeReceiptService.createFeeReceipt(feeReceiptDto);
            response.setStatus("success");
            response.setMessage("Fee receipt created Successfully");
            response.setMCode("200");
            response.setData(savedFeeReceipt);
            return response;

        } catch (Exception e) {
            response.setStatus("error");
            response.setMessage(e.getMessage());
            response.setMCode("500");
            response.setData(null);
            return response;
        }
    }

    // Update Controller
    @PutMapping("/update/{id}")
    public ApiResponse<FeeReceipt> updateFeeReceipt(@PathVariable("id") Long id, @RequestBody FeeReceiptDto feeReceiptDto) {
        ApiResponse<FeeReceipt> response = new ApiResponse<>();
        try {
            FeeReceipt savedFeeReceipt = feeReceiptService.updateFeeReceipt(id, feeReceiptDto);
            response.setStatus("success");
            response.setMessage("Fee receipt updated Successfully");
            response.setMCode("200");
            response.setData(savedFeeReceipt);
            return response;

        } catch (Exception e) {
            response.setStatus("error");
            response.setMessage(e.getMessage());
            response.setMCode("500");
            response.setData(null);
            return response;
        }
    }

    // Get All fee receipt Controller

    @GetMapping("/all")
    public ApiResponse<List<FeeReceipt>> getAllFeeReceipt() {
        ApiResponse<List<FeeReceipt>> response = new ApiResponse<>();
        try {
            List<FeeReceipt> savedFeeReceipt = feeReceiptService.getAllFeeReceipts();
            response.setStatus("success");
            response.setMessage("Fee receipt All Successfully Fetched");
            response.setMCode("200");
            response.setData(savedFeeReceipt);
            return response;

        } catch (Exception e) {
            response.setStatus("error");
            response.setMessage(e.getMessage());
            response.setMCode("500");
            response.setData(null);
            return response;
        }
    }

    // Get only one fee receipt Controller
    @GetMapping("/{id}")
    public ApiResponse<FeeReceipt> singleFeeReceipt(@PathVariable("id") Long id) {
        ApiResponse<FeeReceipt> response = new ApiResponse<>();
        try {
            Optional<FeeReceipt> feeReceipt = feeReceiptService.getFeeReceiptById(id);
            response.setStatus("success");
            response.setMessage("Fee receipt Successfully Fetched");
            response.setMCode("200");
            response.setData(feeReceipt.get());
            return response;

        } catch (Exception e) {
            response.setStatus("error");
            response.setMessage(e.getMessage());
            response.setMCode("500");
            response.setData(null);
            return response;
        }
    }

    // Get only one fee receipt Controller
    @DeleteMapping("/{id}")
    public ApiResponse<FeeReceipt> deleteFeeReceipt(@PathVariable("id") Long id) {
        ApiResponse<FeeReceipt> response = new ApiResponse<>();
        try {
            Optional<FeeReceipt> feeReceipt = feeReceiptService.deleteFeeReceipt(id);
            response.setStatus("success");
            response.setMessage("Fee receipt Successfully Fetched");
            response.setMCode("200");
            response.setData(feeReceipt.get());
            return response;

        } catch (Exception e) {
            response.setStatus("error");
            response.setMessage(e.getMessage());
            response.setMCode("500");
            response.setData(null);
            return response;
        }
    }


}
