package com.assignment.asm03.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
//Đối tượng bác sĩ bao gồm các thuộc tính:
@Entity
@Getter
@Setter
@Table(name="doctor")
@ToString
@NoArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "general_introduction")
    private String generalIntroduction;//Giới thiệu chung

    @Column(name = "educational_background")
    private String educationalBackground;//Qúa trình dào tạo

    @Column(name = "achievements")
    private String achievements;//Thành tựu đạt được

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="hospital_id", referencedColumnName = "id")
    private Hospital hospital;//Bệnh viện tiếp quản

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "specialization_id", referencedColumnName = "id")
    private Specialization specialization;//Chuyên khoa phụ trách

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;//Thông tin cá nhân

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor")
    @ToString.Exclude
    private List<Appointment> appointments;

    public Doctor(String generalIntroduction, String educationalBackground, String achievements, Hospital hospital, Specialization specialization, User user) {
        this.generalIntroduction = generalIntroduction;
        this.educationalBackground = educationalBackground;
        this.achievements = achievements;
        this.hospital = hospital;
        this.specialization = specialization;
        this.user = user;
    }
}
