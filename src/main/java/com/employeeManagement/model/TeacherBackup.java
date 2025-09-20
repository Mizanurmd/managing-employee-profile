package com.employeeManagement.model;

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
    private String gender;
    private String highestEducation;
    private String profileImagePath;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "teacher_backup_skills", joinColumns = @JoinColumn(name = "teacher_id"))
    @Column(name = "skills")
    private List<String> skills = new ArrayList<>();

    private LocalDate deletedAt;
}
