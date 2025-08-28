package com.employeeManagement.service;

import com.employeeManagement.dto.TeacherRequestDto;
import com.employeeManagement.dto.TeacherResponseDto;
import org.springframework.data.domain.Page;


public interface TeacherService {
    TeacherResponseDto addTeacher(TeacherRequestDto teacherRequestDto, String imagePath);

    TeacherResponseDto updateTeacher(Long id, TeacherRequestDto teacherRequestDto);

    TeacherResponseDto deleteTeacher(Long id);

    TeacherResponseDto getTeacherById(Long id);

    Page<TeacherResponseDto> getAllTeachers(int page, int size, String sortBy, String order);

    Page<TeacherResponseDto> searchTeacher(String name, String email, String mobile, int page, int size);
}
