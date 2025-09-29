package com.employeeManagement.paymentDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankPaymentDto {
    private String bankAccountNumber;
    private String bankName;
    private String referenceNumber;
}
