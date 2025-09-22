package com.employeeManagement.controller;

import com.employeeManagement.dto.ApiResponse;
import com.employeeManagement.dto.StudentDto;
import com.employeeManagement.dto.StudentResponseDto;
import com.employeeManagement.model.Student;
import com.employeeManagement.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/students")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<StudentResponseDto>> saveStudent(
            @RequestPart("studentDto") String studentDtoJson,   // raw JSON string
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws JsonProcessingException {

        // Deserialize JSON to StudentDto
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // handle LocalDate
        StudentDto studentDto = mapper.readValue(studentDtoJson, StudentDto.class);

        ApiResponse<StudentResponseDto> response = new ApiResponse<>();
        try {
            // Call service which returns StudentResponseDto
            StudentResponseDto savedStudent = studentService.addStudent(studentDto, file);

            response.setStatus("Success");
            response.setMessage("Student created successfully");
            response.setMCode("200");
            response.setData(savedStudent);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.setStatus("Error");
            response.setMessage("Create Student Failed: " + e.getMessage());
            response.setMCode("500");
            response.setData(null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}
