package com.employeeManagement.enums;

public enum PaymentMode {
    CASH,
    CARD,
    NAGAD,
    BKASH,
    BANK_TRANSFER,
    ONLINE;

    // Helper to check allowed payment modes
    public static boolean isAllowed(PaymentMode mode) {
        return switch (mode) {
            case CASH,CARD, NAGAD, BKASH, BANK_TRANSFER, ONLINE -> true;
            default -> false;
        };
    }
}
