package com.assignment.asm03.repository;

import com.assignment.asm03.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    @Query("SELECT p FROM Patient p LEFT JOIN FETCH p.appointments WHERE p.id = :id")
    Patient findByPatientWithAppointments(@Param("id") int id);

    @Query("SELECT p FROM Patient p WHERE p.user.id = :id")
    Patient findByUserId(@Param("id") int id);
}
