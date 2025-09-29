package com.employeeManagement.service;

import com.employeeManagement.attachement.PdfFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface PdfFileService {
    PdfFile createPdfFile(MultipartFile file, String uploadedBy);

    PdfFile updatePdfFile(Long id, MultipartFile file, String uploadedBy);

    Optional<PdfFile> deletePdfFile(Long id);

    Optional<PdfFile> getPdfFile(Long id);

    List<PdfFile> getAllPdfFiles();


}
