package com.assignment.asm03.service;

import com.assignment.asm03.entity.Hospital;
import com.assignment.asm03.exception.NotFoundException;
import com.assignment.asm03.repository.HospitalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HospitalServiceTest {
    @Mock
    private HospitalRepository hospitalRepository;
    @InjectMocks
    private HospitalServiceImpl hospitalService;
    private Hospital hospital;

    @BeforeEach
    public void setUp() {
        hospital = new Hospital();
        hospital.setId(1);
        hospital.setName("City Hospital");
        hospital.setAddress("123 Main St");
    }
    @Test
    public void HospitalService_Save_ReturnSavedHospital() {
        when(hospitalRepository.save(any(Hospital.class))).thenReturn(hospital);

        Hospital savedHospital = hospitalService.save(hospital);

        assertNotNull(savedHospital);
        assertEquals(hospital.getId(), savedHospital.getId());
        assertEquals(hospital.getName(), savedHospital.getName());
        assertEquals(hospital.getAddress(), savedHospital.getAddress());
    }
    @Test
    public void HospitalService_FindAll_ReturnListOfHospitals() {
        when(hospitalRepository.findAll()).thenReturn(Arrays.asList(hospital));

        List<Hospital> hospitals = hospitalService.findAll();

        assertNotNull(hospitals);
        assertEquals(1, hospitals.size());
        assertEquals(hospital.getId(), hospitals.get(0).getId());
    }
    @Test
    public void HospitalService_FindAllAndSort_ReturnSortedListOfHospitals() {
        when(hospitalRepository.findAllAndSort()).thenReturn(Arrays.asList(hospital));

        List<Hospital> sortedHospitals = hospitalService.findAllAndSort();

        assertNotNull(sortedHospitals);
        assertEquals(1, sortedHospitals.size());
        assertEquals(hospital.getId(), sortedHospitals.get(0).getId());
    }
    @Test
    public void FindById_ExistingId_ReturnHospital() {
        when(hospitalRepository.findById(1)).thenReturn(Optional.of(hospital));

        Hospital foundHospital = hospitalService.findById(1);

        assertNotNull(foundHospital);
        assertEquals(hospital.getId(), foundHospital.getId());
    }
    @Test
    public void FindById_NonExistingId_ThrowNotFoundException() {
        when(hospitalRepository.findById(anyInt())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            hospitalService.findById(99);
        });

        assertEquals("Hospital can't found", exception.getMessage());
    }
    @Test
    public void FindByNameAndAddress_ReturnListOfHospitals() {
        when(hospitalRepository.findByNameAndAddress("City Hospital", "123 Main St"))
                .thenReturn(Arrays.asList(hospital));

        List<Hospital> foundHospitals = hospitalService.findByNameAndAddress("City Hospital", "123 Main St");

        assertNotNull(foundHospitals);
        assertEquals(1, foundHospitals.size());
        assertEquals(hospital.getId(), foundHospitals.get(0).getId());
    }
}
