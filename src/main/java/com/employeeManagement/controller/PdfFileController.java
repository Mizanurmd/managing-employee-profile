package com.employeeManagement.controller;

import com.employeeManagement.attachement.PdfFile;
import com.employeeManagement.responseDto.ApiResponse;
import com.employeeManagement.service.PdfFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pdf-Files")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PdfFileController {

    private final PdfFileService pdfFileService;

    @Autowired
    public PdfFileController(PdfFileService pdfFileService) {
        this.pdfFileService = pdfFileService;
    }

    @PostMapping("/save-pdf")
    public ApiResponse<PdfFile> uploadPdfFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam("uploadedBy") String uploadedBy) {
        ApiResponse<PdfFile> response = new ApiResponse<>();
        try {
            PdfFile savePdf = pdfFileService.createPdfFile(multipartFile, uploadedBy);
            response.setStatus("success");
            response.setMessage("File uploaded successfully");
            response.setData(savePdf);
            response.setMCode("200");
            return response;

        } catch (Exception e) {
            response.setStatus("error");
            response.setMessage(e.getMessage());
            response.setMCode("500");
            response.setData(null);
            return response;
        }

    }

    @PutMapping("/update/{id}")
    public ApiResponse<PdfFile> updateUploadPdfFile(@PathVariable("id") Long id, @RequestParam("file") MultipartFile multipartFile, @RequestParam("uploadedBy") String uploadedBy) {
        ApiResponse<PdfFile> response = new ApiResponse<>();
        try {
            PdfFile savePdf = pdfFileService.updatePdfFile(id, multipartFile, uploadedBy);
            response.setStatus("success");
            response.setMessage("Updated file uploaded successfully");
            response.setData(savePdf);
            response.setMCode("200");
            return response;

        } catch (Exception e) {
            response.setStatus("error");
            response.setMessage(e.getMessage());
            response.setMCode("500");
            response.setData(null);
            return response;
        }

    }

    @GetMapping("/all")
    public ApiResponse<List<PdfFile>> getAllPdfFiles() {
        ApiResponse<List<PdfFile>> response = new ApiResponse<>();
        try {
            List<PdfFile> allPdfFiles = pdfFileService.getAllPdfFiles();
            response.setStatus("success");
            response.setMessage("All files getting  successfully");
            response.setMCode("200");
            response.setData(allPdfFiles);
            return response;

        } catch (Exception e) {
            response.setStatus("error");
            response.setMessage(e.getMessage());
            response.setMCode("500");
            response.setData(null);
            return response;
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<PdfFile> getPdfFileById(@PathVariable("id") Long id) {
        ApiResponse<PdfFile> response = new ApiResponse<>();
        try {
            Optional<PdfFile> pdfFileId = pdfFileService.getPdfFile(id);
            response.setStatus("success");
            response.setMessage("PDF file found successfully");
            response.setMCode("200");
            response.setData(pdfFileId.get());
            return response;

        } catch (Exception e) {
            response.setStatus("error");
            response.setMessage(e.getMessage());
            response.setMCode("500");
            response.setData(null);
            return response;

        }
    }


    @DeleteMapping("/{id}")
    public ApiResponse<PdfFile> deletePdfFileById(@PathVariable("id") Long id) {
        ApiResponse<PdfFile> response = new ApiResponse<>();
        try {
            Optional<PdfFile> pdfFileId = pdfFileService.deletePdfFile(id);
            response.setStatus("success");
            response.setMessage("PDF file deleted successfully");
            response.setMCode("200");
            response.setData(pdfFileId.get());
            return response;

        } catch (Exception e) {
            response.setStatus("error");
            response.setMessage(e.getMessage());
            response.setMCode("500");
            response.setData(null);
            return response;

        }
    }

}
