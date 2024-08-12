package com.assignment.asm03.service;

import com.assignment.asm03.entity.Specialization;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpecializationsService {
    Specialization save(Specialization specialization);
    List<Specialization> findAll();
    List<Specialization> findAllAndSort();
    List<Specialization> searchSpecialization(String keyword);
    Specialization findById(int id);
    List<Specialization> findByNameAndFeeRange(String name, int minFee, int maxFee);
}
