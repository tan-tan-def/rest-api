package com.assignment.asm03.service;

import com.assignment.asm03.entity.Patient;
import com.assignment.asm03.exception.NotFoundException;
import com.assignment.asm03.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientServiceImplement implements PatientService{

    private final PatientRepository patientRepository;

    @Override
    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient findById(int id) {
        Optional<Patient> result = patientRepository.findById(id);
        Patient patient = null;
        if(!result.isPresent()){
            throw new NotFoundException("Không tìm thấy Patient");
        }
        patient = result.get();
        return patient;
    }

    @Override
    public Patient findByPatientWithAppointments(int id) {
        return patientRepository.findByPatientWithAppointments(id);
    }

    @Override
    public Patient findByUserId(int id) {
        Patient patient = patientRepository.findByUserId(id);
        if(patient==null){
            throw new NotFoundException("Không tìm thấy bệnh nhân theo yêu cầu");
        }
        return patient;
    }
}
