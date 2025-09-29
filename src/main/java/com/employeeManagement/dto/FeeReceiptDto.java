package com.employeeManagement.dto;

import com.employeeManagement.enums.PaymentMode;
import com.employeeManagement.paymentDto.BankPaymentDto;
import com.employeeManagement.paymentDto.CardPaymentDto;
import com.employeeManagement.paymentDto.MobilePaymentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeeReceiptDto {
    private Long id;
    private Long studentId;
    private BigDecimal amountPaid;
    private PaymentMode paymentMode;
    private String transactionId;
    private LocalDateTime paymentDate;
    private String remarks;
    private LocalDateTime createdAt;

    // for CARD
    private CardPaymentDto cardPayment;
    // for NAGAD / BKASH/
    private MobilePaymentDto mobilePayment;
    // for BANK_TRANSFER
    private BankPaymentDto bankPayment;
}
