package com.assignment.asm03.service;

import com.assignment.asm03.entity.Hospital;
import com.assignment.asm03.exception.NotFoundException;
import com.assignment.asm03.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepository hospitalRepository;

    @Override
    public Hospital save(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    @Override
    public List<Hospital> findAll() {
        return hospitalRepository.findAll();
    }

    @Override
    public List<Hospital> findAllAndSort() {
        return hospitalRepository.findAllAndSort();
    }

    @Override
    public Hospital findById(int id) {
        Optional<Hospital> result = hospitalRepository.findById(id);
        Hospital hospital = null;
        if (!result.isPresent()){
            throw new NotFoundException("Hospital can't found");
        }
        hospital = result.get();
        return hospital;
    }

    @Override
    public List<Hospital> findByNameAndAddress(String name, String address) {
        return hospitalRepository.findByNameAndAddress(name,address);
    }
}
