package com.employeeManagement.paymentDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardPaymentDto {
    private String cardNumber;
    private String cardHolderName;
    private String expiryMonth; // MM
    private String expiryYear;  // YYYY
    private String cvv;
}
