package com.assignment.asm03.service;

import com.assignment.asm03.entity.Doctor;
import com.assignment.asm03.exception.NotFoundException;
import com.assignment.asm03.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService{
    private final DoctorRepository doctorRepository;

    @Override
    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor findById(int id) {
        Optional<Doctor> result = doctorRepository.findById(id);
        Doctor doctor;
        if (!result.isPresent()){
            throw new NotFoundException("Không tìm thấy Doctor, vui lòng chọn số lớn hon 0 và nhỏ hơn hoặc bằng "+findAll().size()+" trong doctorId");
        }
        doctor = result.get();
        return doctor;
    }

    @Override
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor findByUserId(int id) {
        Doctor doctor = doctorRepository.findByUserId(id);
        if(doctor==null){
            throw new NotFoundException("Không tìm thấy bác sĩ yêu cầu, vui lòng nhập đúng id bác sĩ");
        }
        return doctor;
    }
}
