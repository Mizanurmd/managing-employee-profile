package com.employeeManagement.model;

import com.employeeManagement.enums.Gender;
import com.employeeManagement.enums.StudentClass;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "students")
public class Student {//one
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 8)
    private String studentIdNo;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 100)
    private String fatherName;

    @Column(nullable = false, length = 100)
    private String motherName;

    @Column(nullable = false, unique = true, length = 20)
    private String admissionNumber;

    @NotNull
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 15)
    private String phoneNumber;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudentClass studentClass;

    @NotNull
    private LocalDate admissionDate;

    @Column(length = 50)
    private String section;

    @Column(length = 50)
    private String rollNumber;

    private String profileImagePath;

    @Column(nullable = false)
    private boolean active = true;

    private LocalDate deletedAt;


}
