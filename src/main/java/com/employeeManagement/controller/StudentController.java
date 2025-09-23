package com.employeeManagement.controller;

import com.employeeManagement.model.Student;
import com.employeeManagement.responseDto.ApiResponse;
import com.employeeManagement.dto.StudentDto;
import com.employeeManagement.responseDto.StudentResponseDto;
import com.employeeManagement.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/students")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StudentController {
    private final StudentService studentService;
    private final ObjectMapper objectMapper;

    @Autowired
    public StudentController(StudentService studentService, ObjectMapper objectMapper) {
        this.studentService = studentService;
        this.objectMapper = objectMapper;
    }


    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<StudentResponseDto>> saveStudent(
            @RequestPart("studentDto") String studentDtoJson,   // raw JSON string
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws JsonProcessingException {

        // Deserialize JSON to StudentDto

        objectMapper.registerModule(new JavaTimeModule()); // handle LocalDate
        StudentDto studentDto = objectMapper.readValue(studentDtoJson, StudentDto.class);

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

    // Update Student Handler
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<StudentResponseDto>> updateStudent(
            @PathVariable("id") Long id,
            @RequestPart("studentDto") String studentDtoJson,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        ApiResponse<StudentResponseDto> response = new ApiResponse<>();
        try {
            StudentDto studentDto = objectMapper.readValue(studentDtoJson, StudentDto.class);
            StudentResponseDto updatedStudent = studentService.updateStudent(id, studentDto, file);
            response.setStatus("Success");
            response.setMessage("Student updated successfully");
            response.setMCode("200");
            response.setData(updatedStudent);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.setStatus("Error");
            response.setMessage("Update Student Failed: " + e.getMessage());
            response.setMCode("500");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    // Get All Student Handler
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        Page<StudentResponseDto> studentPage = studentService.getAllStudents(page, size, sortField, sortDirection);
        Map<String, Object> map = new HashMap<>();
        map.put("students", studentPage.getContent());
        map.put("currentPage", studentPage.getNumber());
        map.put("totalPages", studentPage.getTotalPages());
        map.put("totalElements", studentPage.getTotalElements());
        map.put("sortField", sortField);
        map.put("sortDirection", sortDirection);
        return new ResponseEntity<>(map, HttpStatus.OK);

    }


}
