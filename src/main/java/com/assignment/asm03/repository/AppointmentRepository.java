package com.assignment.asm03.repository;

import com.assignment.asm03.entity.Appointment;
import com.assignment.asm03.entity.Doctor;
import com.assignment.asm03.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByDoctor(Doctor doctor);
    List<Appointment> findByPatient(Patient patient);

}
