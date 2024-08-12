package com.assignment.asm03.entity;

import com.assignment.asm03.common.DateTimeHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

//Đại diện cho thông tin chung của một User(Patient, Admin, Doctor)
@Getter
@Setter
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String gender;

    private String email;

    private String phone;

    private String address;

    @JsonIgnore
    private String password;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="roleId", referencedColumnName = "id")
    private Role role;

    private String description;

    @Column(name = "is_active")
    private boolean isActive;

    @JsonIgnore
    @Column(name="created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Doctor doctor;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Patient patient;

    @JsonIgnore
    @Column(name="updated_at")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;


    public User(String name, String gender, String email, String phone, String address, String password, Role role, boolean isActive) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
        this.createdAt = DateTimeHelper.getCurrentDateTime();
    }

    public User() {
    }
}
