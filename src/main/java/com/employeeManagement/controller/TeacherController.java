package com.employeeManagement.controller;

import com.employeeManagement.responseDto.ApiResponse;
import com.employeeManagement.dto.TeacherRequestDto;
import com.employeeManagement.responseDto.TeacherResponseDto;
import com.employeeManagement.model.Teacher;
import com.employeeManagement.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable("id") long id) {
        Teacher teacher = teacherService.getTeacherById(id);
        return new ResponseEntity<>(teacher, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllTeachers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir

    ) {
        Page<Teacher> teacherPage = teacherService.getAllTeachers(page, size, sortBy, sortDir);
        Map<String, Object> map = new HashMap<>();
        map.put("teachers", teacherPage.getContent());
        map.put("currentPage", teacherPage.getNumber());
        map.put("totalItems", teacherPage.getTotalElements());
        map.put("totalPages", teacherPage.getTotalPages());
        map.put("sortBy", sortBy);
        map.put("sortDir", sortDir);
        return new ResponseEntity<>(map, HttpStatus.OK);

    }

    @GetMapping("/search")
    public Page<TeacherResponseDto> searchTeachers(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String mobile,
            @RequestParam(defaultValue = "") String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size

    ) {

        return teacherService.searchTeacher(name, mobile, email, page, size);

    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable("id") long id) {
        teacherService.deleteTeacher(id);
    }

    // Soft deleted handler
    @DeleteMapping("/soft-delete/{teacherId}")
    public ResponseEntity<ApiResponse<Teacher>> SoftDeleteTeacher(@PathVariable("teacherId") String teacherId) {
        ApiResponse response = new ApiResponse();
        try {
            response.setStatus("Success");
            response.setMessage("Teacher soft deleted and backed up successfully");
            response.setMCode("200");
            response.setData(teacherService.softDeleteTeacher(teacherId));
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setStatus("Error");
            response.setMessage("Soft Delete Teacher Failed");
            response.setMCode("500");
            response.setData(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}