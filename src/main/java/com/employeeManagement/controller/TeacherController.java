package com.employeeManagement.controller;

import com.employeeManagement.dto.TeacherRequestDto;
import com.employeeManagement.dto.TeacherResponseDto;
import com.employeeManagement.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/teachers")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TeacherController {
    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }


    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeacherResponseDto> createTeacher(
            @Valid @ModelAttribute TeacherRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {

        TeacherResponseDto response = teacherService.addTeacher(dto, file);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeacherResponseDto> updateTeacher(
            @PathVariable("id") long id,
            @ModelAttribute TeacherRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {

        TeacherResponseDto response = teacherService.updateTeacher(id, dto, file);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}