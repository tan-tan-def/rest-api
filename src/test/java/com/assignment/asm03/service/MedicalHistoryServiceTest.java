package com.assignment.asm03.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import com.assignment.asm03.entity.MedicalHistory;
import com.assignment.asm03.exception.NotFoundException;
import com.assignment.asm03.repository.MedicalHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;
@ExtendWith(MockitoExtension.class)
public class MedicalHistoryServiceTest {
    @Mock
    private MedicalHistoryRepository medicalHistoryRepository;
    @InjectMocks
    private MedicalHistoryServiceImpl medicalHistoryService;

    private MedicalHistory medicalHistory;

    @BeforeEach
    public void setUp() {
        medicalHistory = new MedicalHistory();
        medicalHistory.setId(1);
        medicalHistory.setTreatment("Nghỉ ngơi và uống thuốc");
    }

    @Test
    public void MedicalHistoryService_Save_ReturnSavedMedicalHistory() {
        when(medicalHistoryRepository.save(any(MedicalHistory.class))).thenReturn(medicalHistory);

        MedicalHistory savedMedicalHistory = medicalHistoryService.save(medicalHistory);

        assertNotNull(savedMedicalHistory);
        assertEquals(medicalHistory.getId(), savedMedicalHistory.getId());
        assertEquals(medicalHistory.getTreatment(), savedMedicalHistory.getTreatment());
    }

    @Test
    public void MedicalHistoryService_FindAll_ReturnListOfMedicalHistories() {
        when(medicalHistoryRepository.findAll()).thenReturn(Arrays.asList(medicalHistory));

        List<MedicalHistory> medicalHistories = medicalHistoryService.findAll();

        assertNotNull(medicalHistories);
        assertEquals(1, medicalHistories.size());
        assertEquals(medicalHistory.getId(), medicalHistories.get(0).getId());
    }

    @Test
    public void FindById_ExistingId_ReturnMedicalHistory() {
        when(medicalHistoryRepository.findById(1)).thenReturn(Optional.of(medicalHistory));

        MedicalHistory foundMedicalHistory = medicalHistoryService.findById(1);

        assertNotNull(foundMedicalHistory);
        assertEquals(medicalHistory.getId(), foundMedicalHistory.getId());
    }

    @Test
    public void FindById_NonExistingId_ThrowNotFoundException() {
        when(medicalHistoryRepository.findById(anyInt())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            medicalHistoryService.findById(99);
        });

        assertEquals("Không tìm thấy Medical History", exception.getMessage());
    }
}
