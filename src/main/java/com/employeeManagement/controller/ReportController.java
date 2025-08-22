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

    //////Controller for Report generated code here/////////////

    @GetMapping("/{format}")
    public String generateReport(@PathVariable String format) throws FileNotFoundException, JRException {
        System.out.println("Pdf is generated-------------------------------");
        return reportService.exportReport(format);

    }
}
