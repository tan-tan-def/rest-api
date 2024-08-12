package com.assignment.asm03.entity;

import com.assignment.asm03.common.DateTimeHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Entity
@Getter
@Setter
@Table(name="appointment")
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "confirm_by_doctor")
    private boolean confirmByDoctor;

    @Column(name = "appointment_time")
    private String appointmentTime;

    @Column(name = "medical_fee")
    private String medicalFee;

    private String reason;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_at")
    private Date createAt;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "doctor_id")
    @JsonIgnore
    private Doctor doctor;

    @Transient
    private String nameDoctor;

    @Transient
    private String namePatient;

    @Transient
    private String genderPatient;

    @Transient
    private String emailPatient;

    @Transient
    private String emailDoctor;

    @Transient
    private String phonePatient;

    @Transient
    private String addressPatient;

    @Transient
    private String hospitalName;


    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private Patient patient;


    public Appointment(String appointmentTime, String medicalFee, String reason, Doctor doctor, Patient patient) {
        this.appointmentTime = appointmentTime;
        this.medicalFee = medicalFee;
        this.reason = reason;
        this.doctor = doctor;
        this.patient = patient;
        this.createAt = DateTimeHelper.getCurrentDateTime();
    }

    public String getNameDoctor() {
        return getDoctor().getUser().getName();
    }

    public String getNamePatient() {
        return getPatient().getUser().getName();
    }

    public String getGenderPatient() {
        return getPatient().getUser().getGender();
    }

    public String getEmailPatient() {
        return getPatient().getUser().getEmail();
    }

    public String getPhonePatient() {
        return getPatient().getUser().getPhone();
    }

    public String getAddressPatient() {
        return getPatient().getUser().getAddress();
    }

    public String getHospitalName() {
        return getDoctor().getHospital().getName();
    }

    public String getEmailDoctor() {
        return getDoctor().getUser().getEmail();
    }

}
