package com.employeeManagement.service;

import com.employeeManagement.dto.CourseDto;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CourseService {
    CourseDto createCourse(CourseDto dto);

    Optional<CourseDto> getCourseById(Long id);

    List<CourseDto> getAllCourses();

    CourseDto updateCourse(Long id, CourseDto dto);

    Optional<CourseDto> deleteCourse(Long id);
}
