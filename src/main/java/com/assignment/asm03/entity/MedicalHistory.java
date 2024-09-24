package com.assignment.asm03.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Lịch sử khám chữa bệnh của một bệnh nhân
@Entity
@Getter
@Setter
@Table(name="medical_history")
@NoArgsConstructor
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // ID của lịch sử khám chữa bệnh

    private String date;

    private String diagnosis; // Chẩn đoán bệnh

    private String treatment; // Điều trị hoặc phương pháp điều trị

    private String notes; // Ghi chú thêm (ví dụ: kết quả xét nghiệm, tư vấn thêm)

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "medicalHistory")
    private Patient patient;

    public MedicalHistory(String diagnosis, String treatment, String notes, String date) {
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.notes = notes;
        this.date = date;
    }
}
