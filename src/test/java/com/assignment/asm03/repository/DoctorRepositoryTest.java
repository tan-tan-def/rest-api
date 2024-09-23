package com.assignment.asm03.repository;

import com.assignment.asm03.entity.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class DoctorRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SpecializationsRepository specializationsRepository;
    private Doctor doctor;
    private Hospital hospital;
    private User user;
    private Specialization specialization;
    private Role role;

    @BeforeEach
    public void setUp(){
        Date date = new Date();
        role = new Role("DOCTOR");
        hospital = new Hospital("Cho Ray", "Sai Gon", "0693125741", 15, "good", date);
        specialization = new Specialization("pediatrician", "providing attentive care", 6, 50000, date);
        user = new User("Nguyen Nam", "Male", "nguyennam1998@gmail.com", "0985147632", "Sài Gòn", "test123", role, true);
        doctor = new Doctor("pediatrician", "medical university", "good student", hospital, specialization, user);

        roleRepository.save(role);
        userRepository.save(user);
        specializationsRepository.save(specialization);
        hospitalRepository.save(hospital);
        doctorRepository.save(doctor);
    }
    @Test
    public void DoctorRepository_FindByUserId_ReturnDoctor(){
        Doctor doctor1 = doctorRepository.findByUserId(1);

        Assertions.assertThat(doctor1).isNotNull();
        Assertions.assertThat(doctor1.getId()).isEqualTo(doctor.getId());
    }

}
