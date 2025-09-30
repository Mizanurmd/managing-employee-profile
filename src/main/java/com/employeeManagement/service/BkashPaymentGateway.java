package com.employeeManagement.service;


import java.math.BigDecimal;

public interface BkashPaymentGateway {
    boolean verifyTransaction(String trxId, String mobile, BigDecimal amount);
}
