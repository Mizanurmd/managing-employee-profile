package com.employeeManagement.model;

import com.employeeManagement.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teacher_backup")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherBackup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teacherId;
    private String name;
    private String mobile;
    private String email;
    private String nid;
    private LocalDate dateOfBirth;
    private String presentAddress;
    private String permanentAddress;
    private Gender gender;
    private String highestEducation;
    private String profileImagePath;

    @Column(length = 1000)
    private String skills;

    private LocalDate deletedAt;
}
