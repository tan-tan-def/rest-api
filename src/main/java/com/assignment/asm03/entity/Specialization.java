package com.assignment.asm03.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

//specializations đại diện thông tin một chuyên khoa(vd: nội khoa, răng hàm mặt, tim mạch,...)
@Entity
@Getter
@Setter
@Table(name="specializations")
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    @Column(name = "sum_booking")
    private int sumBooking;

    private int fee;

    @Column(name="created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "specialization")
    @JsonIgnore
    private List<Doctor> doctors;

    public Specialization(String name, String description, int sumBooking, int fee ,Date createdAt) {
        this.name = name;
        this.description = description;
        this.sumBooking = sumBooking;
        this.fee = fee;
        this.createdAt = createdAt;
    }

    public Specialization() {
    }
}
