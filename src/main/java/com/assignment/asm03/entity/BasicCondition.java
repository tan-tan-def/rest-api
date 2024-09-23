package com.assignment.asm03.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Các thông tin về bệnh lí của một bệnh nhân
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "basic_condition")
public class BasicCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "condition_name")
    private String conditionName; // Tên bệnh lý cơ bản

    private String description; // Mô tả chi tiết về bệnh lý

    private String treatment; // Điều trị hoặc phương pháp điều trị cho bệnh lý

    private String severity; // Mức độ nghiêm trọng của bệnh lý (ví dụ: nhẹ, vừa, nặng)

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @JsonIgnore
    private Patient patient;

    public BasicCondition(String conditionName, String description, String treatment, String severity) {
        this.conditionName = conditionName;
        this.description = description;
        this.treatment = treatment;
        this.severity = severity;
    }

    public BasicCondition(int id, String conditionName, String description, String treatment, String severity) {
        this.id = id;
        this.conditionName = conditionName;
        this.description = description;
        this.treatment = treatment;
        this.severity = severity;
    }
}
