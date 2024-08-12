package com.assignment.asm03.service;

import com.assignment.asm03.entity.Patient;

public interface PatientService {
    Patient save(Patient patient);
    Patient findById(int id);
    Patient findByPatientWithAppointments(int id);
    Patient findByUserId(int id);
}
