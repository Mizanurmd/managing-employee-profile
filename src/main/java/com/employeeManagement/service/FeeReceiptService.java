package com.employeeManagement.service;

import com.employeeManagement.dto.FeeReceiptDto;
import com.employeeManagement.model.FeeReceipt;

import java.util.List;
import java.util.Optional;

public interface FeeReceiptService {
    FeeReceipt createFeeReceipt(FeeReceiptDto feeReceiptDto);

    FeeReceipt updateFeeReceipt(Long id, FeeReceiptDto feeReceiptDto);

    Optional<FeeReceipt> deleteFeeReceipt(Long id);

    Optional<FeeReceipt> getFeeReceiptById(Long id);

    List<FeeReceipt> getAllFeeReceipts();
}
