package com.employeeManagement.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "employees", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"employeeId"}),
        @UniqueConstraint(columnNames = {"nid"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @Column(length = 8, unique = true, nullable = false)
    private String id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 11)
    private String mobile;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, length = 17)
    private String nid;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "present_address", length = 200)
    private String presentAddress;

    @Column(name = "permanent_address", length = 200)
    private String permanentAddress;

    @Column(nullable = false, length = 10)
    private String gender;

    @Column(length = 500)
    private String skills;

    @Column(name = "highest_education", nullable = false, length = 100)
    private String highestEducation;


    @Lob
    private byte[] profileImage;
    private String imageName;
    private String imageType;
    private Long imageSize;
}
