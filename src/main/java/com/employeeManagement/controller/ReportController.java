package com.employeeManagement.controller;

import com.employeeManagement.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String generateReport(@PathVariable String format) throws FileNotFoundException, JRException {
        System.out.println("Pdf is generated-------------------------------");
        return reportService.exportReport(format);

    }

    // Controller for Report generated single employee
    @GetMapping("/{id}/{format}")
    public String generateReportById(@PathVariable("id") String id, @PathVariable("format") String format) throws FileNotFoundException, JRException {
        System.out.println("Pdf is generated-------------------------------");
        return reportService.exportReportById(id, format);

    }
}
