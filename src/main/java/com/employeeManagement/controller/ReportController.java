package com.employeeManagement.controller;

import com.employeeManagement.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/v1/reports")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReportController {
    @Autowired
    private ReportService reportService;

    // Controller for Report generated all employees

    @GetMapping("/{format}")
    public ResponseEntity<byte[]> generateReport(@PathVariable String format)
            throws FileNotFoundException, JRException {

        byte[] report = reportService.exportReport(format);

        HttpHeaders headers = new HttpHeaders();
        if (format.equalsIgnoreCase("pdf")) {
            headers.setContentType(MediaType.APPLICATION_PDF);
        } else {
            headers.setContentType(MediaType.TEXT_HTML);
        }

        headers.setContentDispositionFormData("inline", "employees_report." + format);

        return ResponseEntity.ok()
                .headers(headers)
                .body(report);
    }

    @GetMapping("/{id}/{format}")
    public ResponseEntity<byte[]> generateReportById(
            @PathVariable String id,
            @PathVariable String format) throws FileNotFoundException, JRException {

        byte[] report = reportService.exportReportById(id, format);

        HttpHeaders headers = new HttpHeaders();
        if (format.equalsIgnoreCase("pdf")) {
            headers.setContentType(MediaType.APPLICATION_PDF);
        } else {
            headers.setContentType(MediaType.TEXT_HTML);
        }

        headers.setContentDispositionFormData("inline", "employee_report." + format);

        return ResponseEntity.ok()
                .headers(headers)
                .body(report);
    }

}
