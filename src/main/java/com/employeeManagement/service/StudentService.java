package com.employeeManagement.service;

import com.employeeManagement.dto.StudentDto;
import com.employeeManagement.dto.StudentResponseDto;
import com.employeeManagement.model.Student;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface StudentService {
    StudentResponseDto addStudent(StudentDto studentDto, MultipartFile file);

    Student updateStudent(long id, StudentDto studentDto, MultipartFile imagePath);

    Optional<Student> getStudent(long id);

    Optional<Student> deleteStudent(long id);
}
