package com.assignment.asm03.service;

import com.assignment.asm03.entity.Specialization;
import com.assignment.asm03.exception.NotFoundException;
import com.assignment.asm03.repository.SpecializationsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpecializationsServiceTests {
    @InjectMocks
    private SpecializationsServiceImpl specializationsService;

    @Mock
    private SpecializationsRepository specializationsRepository;

    private Specialization specialization;

    @BeforeEach
    public void setUp() {
        specialization = new Specialization();
        specialization.setId(1);
        specialization.setName("Pediatrics");
        specialization.setFee(50000);
    }

    @Test
    public void SpecializationService_Save_ShouldReturnSavedSpecialization() {
        when(specializationsRepository.save(any(Specialization.class))).thenReturn(specialization);

        Specialization savedSpecialization = specializationsService.save(specialization);

        assertNotNull(savedSpecialization);
        assertEquals(specialization.getId(), savedSpecialization.getId());
        assertEquals(specialization.getName(), savedSpecialization.getName());
    }

    @Test
    public void FindAll_ReturnListOfSpecializations() {
        when(specializationsRepository.findAll()).thenReturn(Arrays.asList(specialization));

        List<Specialization> specializations = specializationsService.findAll();

        assertNotNull(specializations);
        assertEquals(1, specializations.size());
        assertEquals(specialization.getId(), specializations.get(0).getId());
    }

    @Test
    public void FindAllAndSort_ReturnSortedListOfSpecializations() {
        when(specializationsRepository.findAllAndSort()).thenReturn(Arrays.asList(specialization));

        List<Specialization> sortedSpecializations = specializationsService.findAllAndSort();

        assertNotNull(sortedSpecializations);
        assertEquals(1, sortedSpecializations.size());
    }

    @Test
    public void SearchSpecialization_ExistingKeyword_ReturnList() {
        when(specializationsRepository.searchSpecialization("Pediatrics")).thenReturn(Arrays.asList(specialization));

        List<Specialization> foundSpecializations = specializationsService.searchSpecialization("Pediatrics");

        assertNotNull(foundSpecializations);
        assertEquals(1, foundSpecializations.size());
        assertEquals(specialization.getId(), foundSpecializations.get(0).getId());
    }

    @Test
    public void FindById_ExistingId_ReturnSpecialization() {
        when(specializationsRepository.findById(1)).thenReturn(Optional.of(specialization));

        Specialization foundSpecialization = specializationsService.findById(1);

        assertNotNull(foundSpecialization);
        assertEquals(specialization.getId(), foundSpecialization.getId());
    }

    @Test
    public void FindById_NonExistingId_ThrowNotFoundException() {
        when(specializationsRepository.findById(anyInt())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            specializationsService.findById(99);
        });

        assertEquals("Can't found specialization", exception.getMessage());
    }

    @Test
    public void FindByNameAndFeeRange_ReturnSpecializations() {
        when(specializationsRepository.findByNameAndFeeRange("Pediatrics", 40000, 60000))
                .thenReturn(Arrays.asList(specialization));

        List<Specialization> foundSpecializations = specializationsService.findByNameAndFeeRange("Pediatrics", 40000, 60000);

        assertNotNull(foundSpecializations);
        assertEquals(1, foundSpecializations.size());
        assertEquals(specialization.getId(), foundSpecializations.get(0).getId());
    }
}
