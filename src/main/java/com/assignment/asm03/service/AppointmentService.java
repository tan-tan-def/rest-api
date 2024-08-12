package com.assignment.asm03.service;

import com.assignment.asm03.entity.Appointment;
import com.assignment.asm03.entity.Doctor;
import com.assignment.asm03.entity.Patient;

import java.util.List;

public interface AppointmentService {
    Appointment save(Appointment appointment);
    Appointment findById(int id);
    List<Appointment> findByDoctor(Doctor doctor);
    List<Appointment> findByPatient(Patient patient);

}
