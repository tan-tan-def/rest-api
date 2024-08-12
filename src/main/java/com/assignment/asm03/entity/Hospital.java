package com.assignment.asm03.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
//Hospital đại diện cho thông tin của một bệnh viện
@Entity
@Getter
@Setter
@Table(name="hospital")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String address;

    private String phone;

    @Column(name = "sum_booking")
    private int sumBooking;

    private String description;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "hospital")
    @JsonIgnore
    private List<Doctor> doctors;

    @Column(name="created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    public Hospital(String name, String address, String phone, int sumBooking, String description, Date createdAt) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.sumBooking = sumBooking;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Hospital() {
    }
}
