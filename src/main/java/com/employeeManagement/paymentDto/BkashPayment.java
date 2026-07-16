package com.employeeManagement.paymentDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BkashPayment {
    private String bKashNumber;
    private String transactionCode;

}
