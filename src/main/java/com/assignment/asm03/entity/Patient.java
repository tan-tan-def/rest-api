package com.assignment.asm03.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user; //Thông tin cơ bản của một người dùng

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medical_history_id", referencedColumnName = "id")
    private MedicalHistory medicalHistory;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient")
    private Set<Appointment> appointments; //Danh sách các cuộc book lịch khám của môt bệnh nhân


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient")
    private Set<BasicCondition> basicConditions;//Danh sách các bệnh lí cơ bản của một bệnh nhân
}
