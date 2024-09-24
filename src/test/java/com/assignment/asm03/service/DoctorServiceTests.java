package com.assignment.asm03.service;

import com.assignment.asm03.entity.Doctor;
import com.assignment.asm03.exception.NotFoundException;
import com.assignment.asm03.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTests {
    @Mock
    private DoctorRepository doctorRepository;
    @InjectMocks
    private DoctorServiceImpl doctorService;
    private Doctor doctor;

    @BeforeEach
    public void setUp() {
        doctor = new Doctor();
        doctor.setId(1);
        doctor.setEducationalBackground("University");
    }
    @Test
    public void DoctorService_Save_ReturnSavedDoctor() {
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        Doctor savedDoctor = doctorService.save(doctor);

        assertNotNull(savedDoctor);
        assertEquals(doctor.getId(), savedDoctor.getId());
        assertEquals(doctor.getEducationalBackground(), savedDoctor.getEducationalBackground());
    }
    @Test
    public void FindById_ExistingId_ReturnDoctor() {
        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));

        Doctor foundDoctor = doctorService.findById(1);

        assertNotNull(foundDoctor);
        assertEquals(doctor.getId(), foundDoctor.getId());
    }
    @Test
    public void FindById_NonExistingId_ThrowNotFoundException() {
        when(doctorRepository.findById(anyInt())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            doctorService.findById(99);
        });

        assertEquals("Không tìm thấy Doctor, vui lòng chọn số lớn hon 0 và nhỏ hơn hoặc bằng 0 trong doctorId", exception.getMessage());
    }
    @Test
    public void FindAll_ReturnListOfDoctors() {
        when(doctorRepository.findAll()).thenReturn(Arrays.asList(doctor));

        List<Doctor> doctors = doctorService.findAll();

        assertNotNull(doctors);
        assertEquals(1, doctors.size());
        assertEquals(doctor.getId(), doctors.get(0).getId());
    }
    @Test
    public void FindByUserId_ExistingUserId_ReturnDoctor() {
        when(doctorRepository.findByUserId(1)).thenReturn(doctor);

        Doctor foundDoctor = doctorService.findByUserId(1);

        assertNotNull(foundDoctor);
        assertEquals(doctor.getId(), foundDoctor.getId());
    }
    @Test
    public void FindByUserId_NonExistingUserId_ThrowNotFoundException() {
        when(doctorRepository.findByUserId(anyInt())).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            doctorService.findByUserId(99);
        });
        assertEquals("Không tìm thấy bác sĩ yêu cầu, vui lòng nhập đúng id bác sĩ", exception.getMessage());
    }
}