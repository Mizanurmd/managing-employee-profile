package com.employeeManagement.repository;

import com.employeeManagement.model.FeeReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeReceiptRepository extends JpaRepository<FeeReceipt, Long> {
    boolean existsByTransactionId(String transactionId);

}
