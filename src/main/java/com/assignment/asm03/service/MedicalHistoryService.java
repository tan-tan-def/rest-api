package com.assignment.asm03.service;

import com.assignment.asm03.entity.MedicalHistory;

import java.util.List;

public interface MedicalHistoryService {
    MedicalHistory save(MedicalHistory medicalHistory);
    List<MedicalHistory> findAll();
    MedicalHistory findById(int id);
}
