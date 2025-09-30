package com.employeeManagement.serviceImpl;

import com.employeeManagement.service.NagadPaymentGateway;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class NagadPaymentGatewayImpl implements NagadPaymentGateway {

    @Override
    public boolean verifyTransaction(String trxId, String mobile, BigDecimal amount) {
        // TODO: Call Nagad API here with trxId, mobile, amount
        System.out.println("Mock Nagad API Call for trxId=" + trxId);
        return true;
    }
}
