package com.employeeManagement.controller;

import com.employeeManagement.dto.CourseDto;
import com.employeeManagement.model.Course;
import com.employeeManagement.responseDto.ApiResponse;
import com.employeeManagement.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/courses")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<CourseDto>> saveCourse(@RequestBody CourseDto courseDto) {
        ApiResponse<CourseDto> response = new ApiResponse<>();
        try {
            CourseDto savedCourse = courseService.createCourse(courseDto);

            response.setStatus("Success");
            response.setMessage("Course Saved Successfully");
            response.setMCode("200");
            response.setData(savedCourse);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.setStatus("Error");
            response.setMessage(e.getMessage());
            response.setMCode("500");
            response.setData(null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Single course data handler

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseDto>> getCourseById(@PathVariable("id") long id) {
        ApiResponse<CourseDto> response = new ApiResponse<>();

        try {
            Optional<CourseDto> course = courseService.getCourseById(id);
            if (course.isPresent()) {
                response.setStatus("Success");
                response.setMessage("Course Retrieved Successfully");
                response.setMCode("200");
                response.setData(course.get());
                return ResponseEntity.ok(response);
            } else {
                response.setStatus("Error");
                response.setMessage("Course Not Found");
                response.setMCode("404");
                response.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception e) {
            response.setStatus("Error");
            response.setMessage(e.getMessage());
            response.setMCode("500");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // All Courses Handler

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CourseDto>>> getAllCourses() {
        ApiResponse<List<CourseDto>> response = new ApiResponse<>();
        List<CourseDto> courses = courseService.getAllCourses();
        try {
            response.setStatus("Success");
            response.setMessage("Course Retrieved Successfully");
            response.setMCode("200");
            response.setData(courses);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.setStatus("Error");
            response.setMessage(e.getMessage());
            response.setMCode("500");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    // Update Course Handler
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<CourseDto>> updateCourse(@PathVariable("id") long id, @RequestBody CourseDto courseDto) {
        ApiResponse<CourseDto> response = new ApiResponse<>();
        try {
            CourseDto updatedCourse = courseService.updateCourse(id, courseDto);
            response.setStatus("Success");
            response.setMessage("Course Updated Successfully");
            response.setMCode("200");
            response.setData(updatedCourse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus("Error");
            response.setMessage(e.getMessage());
            response.setMCode("500");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    // Delete course Handler

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseDto>> deleteCourse(@PathVariable("id") long id) {
        ApiResponse<CourseDto> response = new ApiResponse<>();

        try {
            Optional<CourseDto> courseOpt = courseService.deleteCourse(id);

            if (courseOpt.isPresent()) {
                response.setStatus("Success");
                response.setMessage("Course Deleted Successfully");
                response.setMCode("200");
                response.setData(courseOpt.get()); // unwrap Optional
                return ResponseEntity.ok(response);
            } else {
                response.setStatus("Error");
                response.setMessage("Course not found");
                response.setMCode("404");
                response.setData(null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception e) {
            response.setStatus("Error");
            response.setMessage(e.getMessage());
            response.setMCode("500");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}
