package com.assignment.asm03.service;

import com.assignment.asm03.entity.Patient;
import com.assignment.asm03.exception.NotFoundException;
import com.assignment.asm03.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
    @InjectMocks
    private PatientServiceImplement patientService;

    @Mock
    private PatientRepository patientRepository;

    private Patient patient;

    @BeforeEach
    public void setUp() {
        patient = new Patient();
        patient.setId(1);
    }
    @Test
    public void PatientService_Save_ReturnSavedPatient() {
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        Patient savedPatient = patientService.save(patient);

        assertNotNull(savedPatient);
        assertEquals(patient.getId(), savedPatient.getId());
    }

    @Test
    public void FindById_ExistingId_ShouldReturnPatient() {
        when(patientRepository.findById(1)).thenReturn(Optional.of(patient));

        Patient foundPatient = patientService.findById(1);

        assertNotNull(foundPatient);
        assertEquals(patient.getId(), foundPatient.getId());
    }

    @Test
    public void FindById_NonExistingId_ShouldThrowNotFoundException() {
        when(patientRepository.findById(anyInt())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            patientService.findById(99);
        });

        assertEquals("Không tìm thấy Patient", exception.getMessage());
    }

    @Test
    public void FindByPatientWithAppointments_ExistingId_ShouldReturnPatientWithAppointments() {
        when(patientRepository.findByPatientWithAppointments(1)).thenReturn(patient);

        Patient foundPatient = patientService.findByPatientWithAppointments(1);

        assertNotNull(foundPatient);
        assertEquals(patient.getId(), foundPatient.getId());
    }

    @Test
    public void FindByUserId_ExistingUserId_ShouldReturnPatient() {
        when(patientRepository.findByUserId(1)).thenReturn(patient);

        Patient foundPatient = patientService.findByUserId(1);

        assertNotNull(foundPatient);
        assertEquals(patient.getId(), foundPatient.getId());
    }

    @Test
    public void FindByUserId_NonExistingUserId_ShouldThrowNotFoundException() {
        when(patientRepository.findByUserId(anyInt())).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            patientService.findByUserId(99);
        });

        assertEquals("Không tìm thấy bệnh nhân theo yêu cầu", exception.getMessage());
    }
}
