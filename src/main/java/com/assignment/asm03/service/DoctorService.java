package com.assignment.asm03.service;

import com.assignment.asm03.entity.Doctor;

import java.util.List;

public interface DoctorService {
    Doctor save(Doctor doctor);
    Doctor findById(int id);
    List<Doctor> findAll();
    Doctor findByUserId(int id);
}
