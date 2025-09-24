package com.employeeManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "address")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Address { //many
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;

    private String street;
    private String city;
    private String state;
    private String presentAddress;
    private String permanentAddress;

    @ManyToOne()
    @JoinColumn(name = "student_id")
    @JsonIgnore()
    private Student student;
}
