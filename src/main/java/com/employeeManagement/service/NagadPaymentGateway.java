package com.employeeManagement.service;

import java.math.BigDecimal;

public interface NagadPaymentGateway {
    boolean verifyTransaction(String trxId, String mobile, BigDecimal amount);
}
