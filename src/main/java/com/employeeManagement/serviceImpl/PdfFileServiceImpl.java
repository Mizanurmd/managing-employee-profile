package com.employeeManagement.serviceImpl;

import com.employeeManagement.attachement.PdfFile;
import com.employeeManagement.repository.PdfFileRepository;
import com.employeeManagement.service.PdfFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PdfFileServiceImpl implements PdfFileService {

    @Value("${file.upload-pdf-dir}")
    private String pdfDirectory;

    private final PdfFileRepository pdfFileRepository;

    @Autowired
    public PdfFileServiceImpl(PdfFileRepository pdfFileRepository) {
        this.pdfFileRepository = pdfFileRepository;
    }

    @Override
    public PdfFile createPdfFile(MultipartFile file, String uploadedBy) {
        PdfFile newPdfFile = new PdfFile();
        if (file != null && !file.isEmpty()) {
            try {
                Path path = Paths.get(pdfDirectory).toAbsolutePath().normalize();
                // Create directory if not exists
                Files.createDirectories(path);
                // Clean file name
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                fileName = fileName.replace(" ", "_"); // optional: replace spaces
                Path targetLocation = Paths.get(pdfDirectory).resolve(fileName);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                // Store this relative path in DB

                // Save metadata
                newPdfFile.setFileName(fileName); // just the name
                newPdfFile.setFilePath("/pdf/" + fileName);
                newPdfFile.setFileSize(file.getSize());
                newPdfFile.setFileType(file.getContentType());
                newPdfFile.setCreatedAt(LocalDateTime.now());
                newPdfFile.setUploadedBy(uploadedBy);
                newPdfFile.setStatus(true);

            } catch (Exception e) {
                throw new RuntimeException("File upload failed: " + e.getMessage(), e);
            }
        } else {
            throw new IllegalArgumentException("File must not be empty");
        }
        return pdfFileRepository.save(newPdfFile);
    }

    @Override
    public PdfFile updatePdfFile(Long id, MultipartFile file, String uploadedBy) {
        PdfFile updatedPdfFile = pdfFileRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found"));
        if (file != null && !file.isEmpty()) {
            try {
                Path path = Paths.get(pdfDirectory).toAbsolutePath().normalize();
                // Create directory if not exists
                Files.createDirectories(path);
                // Clean file name
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                fileName = fileName.replace(" ", "_"); // optional: replace spaces
                Path targetLocation = Paths.get(pdfDirectory).resolve(fileName);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                // Store this relative path in DB

                // Save metadata
                updatedPdfFile.setFileName(fileName);
                updatedPdfFile.setFilePath("/pdf/" + fileName);
                updatedPdfFile.setFileSize(file.getSize());
                updatedPdfFile.setFileType(file.getContentType());
                updatedPdfFile.setUploadedAt(LocalDateTime.now());
                updatedPdfFile.setUploadedBy(uploadedBy);
                updatedPdfFile.setStatus(true);

            } catch (Exception e) {
                throw new RuntimeException("File upload failed: " + e.getMessage(), e);
            }
        } else {
            throw new IllegalArgumentException("File must not be empty");
        }
        return pdfFileRepository.save(updatedPdfFile);
    }

    @Override
    public Optional<PdfFile> deletePdfFile(Long id) {
        return pdfFileRepository.findById(id) .map(pdf -> {
            pdfFileRepository.delete(pdf);
            return pdf;
        });
    }

    @Override
    public Optional<PdfFile> getPdfFile(Long id) {
        return pdfFileRepository.findById(id);
    }

    @Override
    public List<PdfFile> getAllPdfFiles() {
        return pdfFileRepository.findAll();
    }
}
