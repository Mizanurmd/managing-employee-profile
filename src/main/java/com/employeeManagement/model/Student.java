package com.employeeManagement.model;

import com.employeeManagement.enums.Gender;
import com.employeeManagement.enums.StudentClass;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true, nullable = false, length = 8)
    private String studentId;
    // Personal Information
    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 20)
    private String admissionNumber;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    // Many-to-Many Skills
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "student_skills",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills = new ArrayList<>();
    // Contact Information
    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 15)
    private String phoneNumber;

    @Column(length = 200)
    private String presentAddress;

    @Column(length = 200)
    private String permanentAddress;

    // Academic Information
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudentClass studentClass; // Class 1 -> HSC

    @Column(nullable = false)
    private LocalDate admissionDate;

    @Column(length = 50)
    private String section; // e.g., A, B, C

    @Column(length = 50)
    private String rollNumber;

    //Image
    private String profileImagePath;

    // Status
    @Column(nullable = false)
    private boolean active = true;

    @Column
    private LocalDate deletedAt;


}
