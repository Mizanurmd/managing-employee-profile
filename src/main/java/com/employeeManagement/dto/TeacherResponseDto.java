package com.employeeManagement.dto;

import com.employeeManagement.enums.Gender;
import com.employeeManagement.model.Teacher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherResponseDto {
    private String teacherId;
    private String name;
    private String mobile;
    private String email;
    private String nid;
    private LocalDate dateOfBirth;
    private String presentAddress;
    private String permanentAddress;
    private Gender gender;     // keep enum for clarity
    private String highestEducation;
    private List<String> skills;
    private String profileImagePath;


}
