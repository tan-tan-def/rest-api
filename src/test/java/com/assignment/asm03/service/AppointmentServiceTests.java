package com.assignment.asm03.service;

import com.assignment.asm03.entity.*;
import com.assignment.asm03.repository.AppointmentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTests {
    @Mock
    private AppointmentRepository appointmentRepository;
    @InjectMocks
    private AppointmentServiceImpl appointmentService;
    private Appointment appointment;
    private Doctor doctor;
    private Patient patient;

    @BeforeEach
    public void setUp(){
        Date date = new Date();
        Role role = new Role("DOCTOR");
        Hospital hospital = new Hospital("Cho Ray", "Sai Gon", "0693125741", 15, "good", date);
        Specialization specialization = new Specialization("pediatrician", "providing attentive care", 6, 50000, date);
        User user = new User("Nguyen Nam", "Male", "nguyennam1998@gmail.com", "0985147632", "Sài Gòn", "test123", role, true);
        doctor = new Doctor("pediatrician", "medical university", "good student", hospital, specialization, user);
        MedicalHistory medicalHistory = new MedicalHistory("ho", "tri cam", "khong uong da lanh", "25/09/2023");
        patient = new Patient(user, medicalHistory);
        appointment = new Appointment("27/08/2024", "50000", "ho", doctor, patient);
    }
    @Test
    public void AppointmentService_Save_ReturnAppointment(){
        when(appointmentRepository.save(Mockito.any(Appointment.class))).thenReturn(appointment);
        Appointment saveAppointment = appointmentService.save(appointment);
        Assertions.assertThat(saveAppointment).isNotNull();
        Assertions.assertThat(saveAppointment.getId()).isEqualTo(appointment.getId());
    }
    @Test
    public void AppointmentService_FindById_ReturnAppointment(){
        int appointmentId = 1;
        appointment.setId(appointmentId);
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));
        Appointment foundAppointment = appointmentService.findById(appointmentId);
        Assertions.assertThat(foundAppointment).isNotNull();
    }
    @Test
    public void AppointmentService_FindByDoctor_ReturnListAppointment(){
        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(appointment);
        when(appointmentRepository.findByDoctor(doctor)).thenReturn(appointmentList);
        List<Appointment> appointments = appointmentService.findByDoctor(doctor);
        Assertions.assertThat(appointments.get(0).getId()).isEqualTo(appointmentList.get(0).getId());
        Assertions.assertThat(appointments).hasSize(1);
    }
    @Test
    public void AppointmentService_FindByPatient_ReturnListAppointment(){
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);
        when(appointmentRepository.findByPatient(patient)).thenReturn(appointments);
        List<Appointment> appointmentList = appointmentService.findByPatient(patient);
        Assertions.assertThat(appointmentList).hasSize(1);
        Assertions.assertThat(appointmentList.get(0).getId()).isEqualTo(appointments.get(0).getId());
    }
}
