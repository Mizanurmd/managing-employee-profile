package com.employeeManagement.service;

import com.employeeManagement.model.Employee;
import com.employeeManagement.repository.EmployeeRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    @Autowired
    private EmployeeRepository empRepo;

    public byte[] exportReport(String reportFormat) throws FileNotFoundException, JRException {
        // Fetch data
        List<Employee> employees = empRepo.findAll();

        // Load JRXML file
        File file = ResourceUtils.getFile("classpath:reports/all_employees_report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        // Data source
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employees);

        // Parameters
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("CreatedBy", "MD. Mizanur Rahman");

        // Fill report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Export to required format
        if (reportFormat.equalsIgnoreCase("pdf")) {
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } else if (reportFormat.equalsIgnoreCase("html")) {
            // For HTML you could export to a file OR return as string
            return JasperExportManager.exportReportToHtmlFile(String.valueOf(jasperPrint)).getBytes();
        }

        throw new IllegalArgumentException("Unsupported report format: " + reportFormat);
    }

    // Get employee by id method
    public byte[] exportReportById(String id, String reportFormat)
            throws FileNotFoundException, JRException {

        // 🔹 Fetch employee by ID
        Employee employee = empRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        // 🔹 Jasper expects a collection, so wrap employee in a List
        List<Employee> employeeList = Collections.singletonList(employee);

        // 🔹 Load and compile the JRXML file
        File file = ResourceUtils.getFile("classpath:reports/employee_report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        // 🔹 Data source
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employeeList);

        // 🔹 Report parameters
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("CreatedBy", "MD. Mizanur Rahman");
        parameters.put("id", id);

        // 🔹 Fill the report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // 🔹 Export depending on format
        if (reportFormat.equalsIgnoreCase("pdf")) {
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } else if (reportFormat.equalsIgnoreCase("html")) {
            return JasperExportManager.exportReportToHtmlFile(String.valueOf(jasperPrint)).getBytes(); // return HTML as bytes
        }

        throw new IllegalArgumentException("Unsupported format: " + reportFormat);
    }


}
