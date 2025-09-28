package com.employeeManagement.serviceImpl;

import com.employeeManagement.dto.CourseDto;
import com.employeeManagement.model.Course;
import com.employeeManagement.model.Student;
import com.employeeManagement.repository.CourseRepository;
import com.employeeManagement.repository.StudentRepository;
import com.employeeManagement.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    // MapToDto from entity (For API Response)
    private CourseDto mapToDto(Course course) {
        CourseDto courseDto = new CourseDto();
        courseDto = courseDto.toBuilder()
                .courseId(course.getCourseId())
                .courseName(course.getCourseName())
                .courseDescription(course.getCourseDescription())
                .studentId(course.getStudent() != null ? course.getStudent().getId() : null)
                .build();
        return courseDto;
    }

    // MapToEntity from Dto (For DB Response)
    private Course mapDtoToCourse(CourseDto dto, Student student) {
        return Course.builder()
                .courseId(dto.getCourseId())
                .courseName(dto.getCourseName())
                .courseDescription(dto.getCourseDescription())
                .student(student)
                .build();
    }


    @Override
    public CourseDto createCourse(CourseDto dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = mapDtoToCourse(dto, student);
        Course savedCourse = courseRepository.save(course);
        return mapToDto(savedCourse);
    }

    @Override
    public Optional<CourseDto> getCourseById(Long id) {
        return courseRepository.findById(id).map(this::mapToDto);

    }

    @Override
    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll()
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public CourseDto updateCourse(Long id, CourseDto dto) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException((String.format("Course not found for id %d", id))));
        Student student = studentRepository.findById(dto.getStudentId()).orElseThrow(() -> new RuntimeException("Student not found"));
        course.setCourseName(dto.getCourseName());
        course.setCourseDescription(dto.getCourseDescription());
        course.setStudent(student);
        return mapToDto(courseRepository.save(course));
    }

    @Override
    public Optional<CourseDto> deleteCourse(Long id) {
        // Find course by id
        return courseRepository.findById(id)
                .map(course -> {
                    // Delete course if found
                    courseRepository.delete(course);
                    // Map entity to DTO for response
                    return mapToDto(course);
                });
    }


}
