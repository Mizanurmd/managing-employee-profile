package com.employeeManagement.service;

import com.employeeManagement.dto.StudentDto;
import com.employeeManagement.model.Student;

public interface StudentService {
    Student addStudent(StudentDto studentDto);
    Student updateStudent(long id,StudentDto studentDto);
}
