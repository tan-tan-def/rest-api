package com.assignment.asm03.service;

import com.assignment.asm03.entity.Appointment;
import com.assignment.asm03.entity.Doctor;
import com.assignment.asm03.entity.Patient;
import com.assignment.asm03.exception.NotFoundException;
import com.assignment.asm03.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentRepository appointmentRepository;

    @Override
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment findById(int id) {
        Optional<Appointment> result = appointmentRepository.findById(id);
        if(!result.isPresent()){
            throw new NotFoundException("Không tìm thấy Appointment");
        }
        Appointment appointment = result.get();
        return appointment;
    }

    @Override
    public List<Appointment> findByDoctor(Doctor doctor) {
        return appointmentRepository.findByDoctor(doctor);
    }

    @Override
    public List<Appointment> findByPatient(Patient patient) {
        return appointmentRepository.findByPatient(patient);
    }
}
