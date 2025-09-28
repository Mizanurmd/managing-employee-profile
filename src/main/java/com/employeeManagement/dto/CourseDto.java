package com.employeeManagement.dto;

import com.employeeManagement.model.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CourseDto {

    private Long courseId;
    private String courseName;
    private String courseDescription;
    // Option 1: Only studentId reference
    private Long studentId;

}
