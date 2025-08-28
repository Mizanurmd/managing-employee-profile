package com.employeeManagement.model;

import com.employeeManagement.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="teacher")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // internal DB id

    @Column(name = "teacher_id", unique = true, nullable = false, length = 8)
    private String teacherId; // auto generated (00000001, etc.)

    @NotBlank
    @Pattern(regexp = "^[A-Za-z. ]+$", message = "Name can only contain letters, dot, and spaces")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[0-9]{11}$", message = "Mobile must be 11 digits")
    private String mobile;

    @NotBlank
    @Email(message = "Invalid email format")
    private String email;

    @NotNull
    @Column(unique = true)
    @Pattern(regexp = "^(\\d{10}|\\d{17})$", message = "NID must be 10 or 17 digits")
    private String nid;

    @NotNull
    @Past(message = "DOB must be before current date")
    private LocalDate dateOfBirth;

    @Size(max = 200)
    private String presentAddress;

    @Size(max = 200)
    private String permanentAddress;


    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "teacher_skills", joinColumns = @JoinColumn(name = "teacher_id"))
    @Column(name = "skill")
    private List<String> skills = new ArrayList<>() ;

    @NotBlank
    private String highestEducation;

    private String profileImagePath; // store file path
}
