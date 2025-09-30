package com.employeeManagement.serviceImpl;

import com.employeeManagement.service.BkashPaymentGateway;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BkashPaymentGatewayImpl implements BkashPaymentGateway {
    @Override
    public boolean verifyTransaction(String trxId, String mobile, BigDecimal amount) {
        // TODO: Call Bkash API here with trxId, mobile, amount
        System.out.println("Mock Bkash API Call for trxId=" + trxId);
        return true;
    }
}
