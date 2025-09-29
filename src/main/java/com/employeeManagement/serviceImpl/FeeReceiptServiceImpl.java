package com.employeeManagement.serviceImpl;

import com.employeeManagement.dto.FeeReceiptDto;
import com.employeeManagement.enums.PaymentMode;
import com.employeeManagement.model.FeeReceipt;
import com.employeeManagement.model.Student;
import com.employeeManagement.paymentDto.BankPaymentDto;
import com.employeeManagement.paymentDto.CardPaymentDto;
import com.employeeManagement.paymentDto.MobilePaymentDto;
import com.employeeManagement.repository.FeeReceiptRepository;
import com.employeeManagement.repository.StudentRepository;
import com.employeeManagement.service.FeeReceiptService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FeeReceiptServiceImpl implements FeeReceiptService {

    private final FeeReceiptRepository feeReceiptRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public FeeReceiptServiceImpl(FeeReceiptRepository feeReceiptRepository, StudentRepository studentRepository) {
        this.feeReceiptRepository = feeReceiptRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional
    public FeeReceipt createFeeReceipt(FeeReceiptDto feeReceiptDto) {
        //Validation Payment mode
        if (!PaymentMode.isAllowed(feeReceiptDto.getPaymentMode())) {
            throw new RuntimeException("Invalid payment mode! Allowed:CASH, CARD, NAGAD, BKASH, BANK_TRANSFER, ONLINE");

        }

        if (feeReceiptRepository.existsByTransactionId(feeReceiptDto.getTransactionId())) {
            throw new RuntimeException("Fee receipt already exists");
        }
        Student student = studentRepository.findById(feeReceiptDto.getStudentId()).orElseThrow(() -> new RuntimeException("Student not found"));

        // Payment processing
        switch (feeReceiptDto.getPaymentMode()) {
            case CARD -> processCardPayment(feeReceiptDto.getCardPayment(), feeReceiptDto.getAmountPaid());
            case NAGAD, BKASH ->
                    processMobilePayment(feeReceiptDto.getMobilePayment(), feeReceiptDto.getAmountPaid(), feeReceiptDto.getPaymentMode());
            case BANK_TRANSFER -> processBankPayment(feeReceiptDto.getBankPayment(), feeReceiptDto.getAmountPaid());
            case ONLINE -> processOnlinePayment(feeReceiptDto.getAmountPaid());
        }

        FeeReceipt feeReceipt = FeeReceipt.builder().student(student).amountPaid(feeReceiptDto.getAmountPaid()).paymentMode(feeReceiptDto.getPaymentMode()).transactionId(feeReceiptDto.getTransactionId()).paymentDate(feeReceiptDto.getPaymentDate()).remarks(feeReceiptDto.getRemarks()).createdAt(LocalDateTime.now()).build();
        return feeReceiptRepository.save(feeReceipt);
    }

    @Override
    public FeeReceipt updateFeeReceipt(Long id, FeeReceiptDto feeReceiptDto) {
        FeeReceipt updateFee = feeReceiptRepository.findById(id).orElseThrow(() -> new RuntimeException("Fee receipt not found"));
        //Validation for payment mode
        if (!PaymentMode.isAllowed(feeReceiptDto.getPaymentMode())) {
            throw new RuntimeException("Invalid payment mode!");
        }
        // Transaction ID check (if changed)
        if (!updateFee.getTransactionId().equals(feeReceiptDto.getTransactionId()) && feeReceiptRepository.existsByTransactionId(feeReceiptDto.getTransactionId())) {
            throw new RuntimeException("Transaction ID already exists!");
        }
        // Update student (if changed)
        if (!updateFee.getStudent().getId().equals(feeReceiptDto.getStudentId())) {
            Student student = studentRepository.findById(feeReceiptDto.getStudentId()).orElseThrow(() -> new RuntimeException("Student not found"));
            updateFee.setStudent(student);
        }
        //  Process payment again (simulate validation)
        switch (feeReceiptDto.getPaymentMode()) {
            case CARD -> processCardPayment(feeReceiptDto.getCardPayment(), feeReceiptDto.getAmountPaid());
            case NAGAD, BKASH ->
                    processMobilePayment(feeReceiptDto.getMobilePayment(), feeReceiptDto.getAmountPaid(), feeReceiptDto.getPaymentMode());
            case BANK_TRANSFER -> processBankPayment(feeReceiptDto.getBankPayment(), feeReceiptDto.getAmountPaid());
            case ONLINE -> processOnlinePayment(feeReceiptDto.getAmountPaid());
        }
        // Update fields
        updateFee.setAmountPaid(feeReceiptDto.getAmountPaid());
        updateFee.setPaymentMode(feeReceiptDto.getPaymentMode());
        updateFee.setTransactionId(feeReceiptDto.getTransactionId());
        updateFee.setPaymentDate(feeReceiptDto.getPaymentDate());
        updateFee.setRemarks(feeReceiptDto.getRemarks());

        return feeReceiptRepository.save(updateFee);
    }

    @Override
    public Optional<FeeReceipt> deleteFeeReceipt(Long id) {
        return feeReceiptRepository.findById(id).map(feeReceipt -> {
            feeReceiptRepository.delete(feeReceipt);
            return feeReceipt;
        });

    }

    @Override
    public Optional<FeeReceipt> getFeeReceiptById(Long id) {
        return feeReceiptRepository.findById(id);
    }

    @Override
    public List<FeeReceipt> getAllFeeReceipts() {
        return feeReceiptRepository.findAll();
    }

    //=====================Start Payment Processing Part ======================///
    // CARD
    private void processCardPayment(CardPaymentDto card, BigDecimal amount) {
        if (card == null) throw new RuntimeException("Card details are required");
        if (card.getCardNumber().length() != 16 || card.getCvv().length() != 3)
            throw new RuntimeException("Invalid card details");

        System.out.println("Processing CARD payment: " + amount + " for card: " + card.getCardNumber());
    }

    // NAGAD / BKASH
    private void processMobilePayment(MobilePaymentDto mobile, BigDecimal amount, PaymentMode mode) {
        if (mobile == null || mobile.getMobileNumber().isBlank() || mobile.getTransactionCode().isBlank())
            throw new RuntimeException(mode + " payment details required");

        System.out.println("Processing " + mode + " payment: " + amount + " for mobile: " + mobile.getMobileNumber());
    }

    // BANK_TRANSFER
    private void processBankPayment(BankPaymentDto bank, BigDecimal amount) {
        if (bank == null || bank.getBankAccountNumber().isBlank() || bank.getReferenceNumber().isBlank())
            throw new RuntimeException("Bank transfer details required");

        System.out.println("Processing BANK_TRANSFER payment: " + amount + " to account: " + bank.getBankAccountNumber());
    }

    // ONLINE (simulate simple online payment)
    private void processOnlinePayment(BigDecimal amount) {
        System.out.println("Processing ONLINE payment: " + amount);
    }
    //=====================End Payment Processing Part ======================///

}
