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
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AppointmentRepositoryTest {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private SpecializationsRepository specializationsRepository;
    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private RoleRepository roleRepository;

    private Role role;
    private Hospital hospital;
    private Specialization specialization;
    private User user;
    private Doctor doctor;
    private MedicalHistory medicalHistory;
    private Patient patient;
    private Appointment appointment;

    @BeforeEach
    public void setUp() {
        Date date = new Date();
        role = new Role("DOCTOR");
        hospital = new Hospital("Cho Ray", "Sai Gon", "0693125741", 15, "good", date);
        specialization = new Specialization("pediatrician", "providing attentive care", 6, 50000, date);
        user = new User("Nguyen Nam", "Male", "nguyennam1998@gmail.com", "0985147632", "Sài Gòn", "test123", role, true);
        doctor = new Doctor("pediatrician", "medical university", "good student", hospital, specialization, user);
        medicalHistory = new MedicalHistory("ho", "tri cam", "khong uong da lanh", "25/09/2023");
        patient = new Patient(user, medicalHistory);
        appointment = new Appointment("27/08/2024", "50000", "ho", doctor, patient);

        // Lưu các đối tượng vào cơ sở dữ liệu
        roleRepository.save(role);
        userRepository.save(user);
        medicalHistoryRepository.save(medicalHistory);
        patientRepository.save(patient);
        specializationsRepository.save(specialization);
        hospitalRepository.save(hospital);
        doctorRepository.save(doctor);
        appointmentRepository.save(appointment);
    }
    @Test
    public void AppointmentRepository_FindByDoctor_ReturnListAppointment(){
        List<Appointment> appointmentList = appointmentRepository.findByDoctor(doctor);
        Assertions.assertThat(appointmentList).isNotNull();
        Assertions.assertThat(appointmentList.size()).isEqualTo(1);
        Assertions.assertThat(appointment.getId()).isEqualTo(appointmentList.get(0).getId());
    }
    @Test
    public void AppointmentRepository_FindByPatient_ReturnListAppointment(){
        List<Appointment> appointmentList = appointmentRepository.findByPatient(patient);
        Assertions.assertThat(appointmentList).isNotNull();
        Assertions.assertThat(appointmentList.size()).isEqualTo(1);
        Assertions.assertThat(appointmentList.get(0).getId()).isEqualTo(patient.getId());
    }
}
