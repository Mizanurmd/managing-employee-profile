package com.employeeManagement.service;

import com.employeeManagement.dto.TeacherRequestDto;
import com.employeeManagement.dto.TeacherResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;


public interface TeacherService {
    TeacherResponseDto addTeacher(TeacherRequestDto teacherRequestDto, MultipartFile imagePath);

    TeacherResponseDto updateTeacher(Long id, TeacherRequestDto teacherRequestDto, MultipartFile imagePath);

    TeacherResponseDto deleteTeacher(Long id);

    TeacherResponseDto getTeacherById(Long id);

    Page<TeacherResponseDto> getAllTeachers(int page, int size, String sortBy, String order);

    Page<TeacherResponseDto> searchTeacher(String name, String email, String mobile, int page, int size);
}
