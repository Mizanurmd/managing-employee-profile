package com.employeeManagement.service;

import com.employeeManagement.dto.StudentDto;
import com.employeeManagement.responseDto.StudentResponseDto;
import com.employeeManagement.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface StudentService {
    StudentResponseDto addStudent(StudentDto studentDto, MultipartFile file);

    StudentResponseDto updateStudent(long id, StudentDto studentDto, MultipartFile imagePath);

    Page<StudentResponseDto> getAllStudents(int page, int size, String sortField, String sortDirection);

    Optional<Student> getStudent(long id);

    Optional<Student> deleteStudent(long id);
}
