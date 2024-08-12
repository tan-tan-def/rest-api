package com.assignment.asm03.service;

import com.assignment.asm03.entity.Hospital;

import java.util.List;

public interface HospitalService {
    Hospital save(Hospital hospital);
    List<Hospital> findAll();
    List<Hospital> findAllAndSort();
    Hospital findById(int id);
    List<Hospital> findByNameAndAddress(String name, String address);
}
