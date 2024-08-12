package com.assignment.asm03.service;

import com.assignment.asm03.entity.MedicalHistory;
import com.assignment.asm03.exception.NotFoundException;
import com.assignment.asm03.repository.MedicalHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicalHistoryServiceImpl implements MedicalHistoryService{
    private final MedicalHistoryRepository medicalHistoryRepository;
    @Override
    public MedicalHistory save(MedicalHistory medicalHistory) {
        return medicalHistoryRepository.save(medicalHistory);
    }

    @Override
    public List<MedicalHistory> findAll() {
        return medicalHistoryRepository.findAll();
    }

    @Override
    public MedicalHistory findById(int id) {
        Optional<MedicalHistory> result = medicalHistoryRepository.findById(id);
        MedicalHistory medicalHistory = null;
        if(!result.isPresent()){
            throw new NotFoundException("Không tìm thấy Medical History");
        }
        medicalHistory = result.get();
        return medicalHistory;
    }
}
