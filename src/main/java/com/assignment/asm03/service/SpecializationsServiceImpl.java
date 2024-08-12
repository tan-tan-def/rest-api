package com.assignment.asm03.service;

import com.assignment.asm03.entity.Specialization;
import com.assignment.asm03.exception.NotFoundException;
import com.assignment.asm03.repository.SpecializationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpecializationsServiceImpl implements SpecializationsService{
    private final SpecializationsRepository specializationsRepository;
    @Override
    public Specialization save(Specialization specialization) {
        return specializationsRepository.save(specialization);
    }

    @Override
    public List<Specialization> findAll() {
        return specializationsRepository.findAll();
    }

    @Override
    public List<Specialization> findAllAndSort() {
        return specializationsRepository.findAllAndSort();
    }

    @Override
    public List<Specialization> searchSpecialization(String keyword) {
        return specializationsRepository.searchSpecialization(keyword);
    }

    @Override
    public Specialization findById(int id) {
        Optional<Specialization> result = specializationsRepository.findById(id);
        Specialization specialization = null;
        if(!result.isPresent()){
            throw new NotFoundException("Can't found specialization");
        }
        specialization = result.get();
        return specialization;
    }

    @Override
    public List<Specialization> findByNameAndFeeRange(String name, int minFee, int maxFee) {
        return specializationsRepository.findByNameAndFeeRange(name,minFee,maxFee);
    }

}
