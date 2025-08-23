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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    @Autowired
    private EmployeeRepository empRepo;

    public String exportReport(String reportFromat) throws FileNotFoundException, JRException {


        String path = "C:\\Users\\88016\\Desktop\\Report";
        List<Employee> pa = empRepo.findAll();

        // load file and compile
        File file = ResourceUtils.getFile("classpath:reports/all_employees_report.jrxml");

        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(pa);

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("Createdby", "MD.Mizanur Rahman");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, dataSource);

        if (reportFromat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\all_employees_report.html");
        }

        if (reportFromat.equalsIgnoreCase("pdf")) {

            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\all_employees_report.pdf");

        }

        return "Report generated in the path : " + path;
    }
}
