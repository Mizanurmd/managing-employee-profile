package com.employeeManagement.paymentDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MobilePaymentDto {
    private String mobileNumber;
    private String transactionCode;
}
