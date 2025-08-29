package com.employeeManagement.service;

import com.employeeManagement.dto.TeacherRequestDto;
import com.employeeManagement.dto.TeacherResponseDto;
import com.employeeManagement.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;


public interface TeacherService {
    TeacherResponseDto addTeacher(TeacherRequestDto teacherRequestDto, MultipartFile imagePath);

    TeacherResponseDto updateTeacher(Long id, TeacherRequestDto teacherRequestDto, MultipartFile imagePath);

    void deleteTeacher(Long id);

    Teacher getTeacherById(Long id);

    Page<Teacher> getAllTeachers(int page, int size, String sortBy, String sortDir);

    Page<TeacherResponseDto> searchTeacher(String name, String email, String mobile, int page, int size);
}
