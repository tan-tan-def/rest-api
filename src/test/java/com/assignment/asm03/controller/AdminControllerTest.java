package com.assignment.asm03.controller;

import com.assignment.asm03.entity.*;
import com.assignment.asm03.model.DoctorIsAddedByAdmin;
import com.assignment.asm03.security.JwtDecoder;
import com.assignment.asm03.security.JwtToPrincipalConverter;
import com.assignment.asm03.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(controllers = AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {
    @MockBean
    private JwtDecoder jwtDecoder;
    @MockBean
    private JwtToPrincipalConverter jwtToPrincipalConverter;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private BCryptPasswordEncoder encoder;
    @MockBean
    private RoleService roleService;
    @MockBean
    private HospitalService hospitalService;
    @MockBean
    private SpecializationsService specializationsService;
    @MockBean
    private DoctorService doctorService;
    @MockBean
    private PatientService patientService;
    @MockBean
    private AppointmentService appointmentService;
    private User user;
    private User userAdmin;
    private User userDoctor;
    private Doctor doctor;
    private DoctorIsAddedByAdmin doctorIsAddedByAdmin;
    private Patient patient;
    private Appointment appointment;
    @BeforeEach
    public void setUp(){
        Role roleUser = new Role(1,"USER");
        Role roleAdmin = new Role(2,"ADMIN");
        Role roleDoctor = new Role(3, "DOCTOR");
        user = new User("Nguyen Nam", "Male", "nguyennam1998@gmail.com", "0985147632", "Sài Gòn", "test123", roleUser, true);
        user.setId(1);
        userAdmin = new User("Nguyen Nam", "Male", "nguyennam1998@gmail.com", "0985147632", "Sài Gòn", "test123", roleAdmin, true);
        userAdmin.setId(1);
        userDoctor = new User("Nguyen Nam", "Male", "nguyennam1998@gmail.com", "0985147632", "Sài Gòn", "test123", roleDoctor, true);
        userDoctor.setId(1);

        Specialization specialization = new Specialization("pediatrician", "providing attentive care", 6, 50000, new Date());
        Hospital hospital = new Hospital("Cho Ray", "Sai Gon", "0693125741", 15, "good", new Date());
        doctor = new Doctor("pediatrician", "medical university", "good student", hospital, specialization, user);

        doctorIsAddedByAdmin = new DoctorIsAddedByAdmin("df","df","helo@gmail.com","0955","fdf","fdd","dfd","dfd","fdf",5,4);

        MedicalHistory medicalHistory = new MedicalHistory("ho", "tri cam", "khong uong da lanh", "25/09/2023");
        patient = new Patient(user, medicalHistory);
        appointment = new Appointment("27/08/2024", "50000", "ho", doctor, patient);


    }
    @Test
    public void LockUser_FailToLock_ReturnBadRequest() throws Exception {
        User anotherUser = new User("Nguyen Nam", "Male", "nguyennam1998@gmail.com", "0985147632", "Sài Gòn", "test123", new Role(2,""), true);

        when(userService.findById(anyInt())).thenReturn(anotherUser);
        when(userService.save(any(User.class))).thenReturn(anotherUser);

        ResultActions resultActions = mockMvc.perform(put("/admin/lock-user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("description",""));
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Vui lòng chọn tài khoản "+user.getRole().getName()));

    }
    @Test
    public void LockUser_SuccessToLock_ReturnOk() throws Exception {
        User anotherUser = new User("Nguyen Nam", "Male", "nguyennam1998@gmail.com", "0985147632", "Sài Gòn", "test123", new Role(1,"USER"), true);

        when(userService.findById(anyInt())).thenReturn(anotherUser);
        when(userService.save(any(User.class))).thenReturn(anotherUser);

        ResultActions resultActions = mockMvc.perform(put("/admin/lock-user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("description",""));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Khóa tài khoản "+ anotherUser.getName()+" thành công"));
    }
    @Test
    public void UnlockUser_FailToUnlock_ReturnBadRequest() throws Exception {
        User anotherUser = new User("Nguyen Nam", "Male", "nguyennam1998@gmail.com", "0985147632", "Sài Gòn", "test123", new Role(2,""), true);

        when(userService.findById(anyInt())).thenReturn(anotherUser);
        when(userService.save(any(User.class))).thenReturn(anotherUser);

        ResultActions resultActions = mockMvc.perform(put("/admin/unlock-user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("description",""));
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Vui lòng chọn tài khoản "+user.getRole().getName()));

    }
    @Test
    public void UnlockUser_SuccessToUnlock_ReturnOk() throws Exception {
        User anotherUser = new User("Nguyen Nam", "Male", "nguyennam1998@gmail.com", "0985147632", "Sài Gòn", "test123", new Role(1,"USER"), true);

        when(userService.findById(anyInt())).thenReturn(anotherUser);
        when(userService.save(any(User.class))).thenReturn(anotherUser);

        ResultActions resultActions = mockMvc.perform(put("/admin/unlock-user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("description",""));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hủy khóa "+anotherUser.getName()+ " thành công"));
    }
    @Test
    public void LockDoctor_FailToLock_ReturnBadRequest() throws Exception {
        User anotherUser = new User("Nguyen Nam", "Male", "nguyennam1998@gmail.com", "0985147632", "Sài Gòn", "test123", new Role(5,""), true);

        when(userService.findById(anyInt())).thenReturn(anotherUser);
        when(userService.save(any(User.class))).thenReturn(anotherUser);

        ResultActions resultActions = mockMvc.perform(put("/admin/lock-doctor/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("description",""));
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Vui lòng chọn tài khoản "+userDoctor.getRole().getName()));

    }
    @Test
    public void LockDoctor_SuccessToLock_ReturnOk() throws Exception {
        User anotherUser = new User("Nguyen Nam", "Male", "nguyennam1998@gmail.com", "0985147632", "Sài Gòn", "test123", new Role(3,"USER"), true);

        when(userService.findById(anyInt())).thenReturn(anotherUser);
        when(userService.save(any(User.class))).thenReturn(anotherUser);

        ResultActions resultActions = mockMvc.perform(put("/admin/lock-doctor/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("description",""));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Khóa tài khoản "+ anotherUser.getName()+" thành công"));
    }
    @Test
    public void UnlockDoctor_FailToUnlock_ReturnBadRequest() throws Exception {
        User anotherUser = new User("Nguyen Nam", "Male", "nguyennam1998@gmail.com", "0985147632", "Sài Gòn", "test123", new Role(2,""), true);

        when(userService.findById(anyInt())).thenReturn(anotherUser);
        when(userService.save(any(User.class))).thenReturn(anotherUser);

        ResultActions resultActions = mockMvc.perform(put("/admin/unlock-doctor/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("description",""));
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Vui lòng chọn tài khoản " + userDoctor.getRole().getName()));

    }
    @Test
    public void UnlockDoctor_SuccessToUnlock_ReturnOk() throws Exception {
        User anotherUser = new User("Nguyen Nam", "Male", "nguyennam1998@gmail.com", "0985147632", "Sài Gòn", "test123", new Role(3,"USER"), true);

        when(userService.findById(anyInt())).thenReturn(anotherUser);
        when(userService.save(any(User.class))).thenReturn(anotherUser);

        ResultActions resultActions = mockMvc.perform(put("/admin/unlock-doctor/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("description",""));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hủy khóa "+anotherUser.getName()+ " thành công"));
    }
    @Test
    public void AddDoctor_FailToAdd_ReturnBadRequest() throws Exception {
        DoctorIsAddedByAdmin doctorIsAddedByAdmin = new DoctorIsAddedByAdmin();
        ResultActions resultActions = mockMvc.perform(post("/admin/add-doctor")
                .param("doctorIsAddedByAdmin", String.valueOf(doctorIsAddedByAdmin))
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void AddDoctor_SuccessToAdd_ReturnCreated() throws Exception {
        when(userService.save(any(User.class))).thenReturn(userDoctor);
        when(doctorService.save(any(Doctor.class))).thenReturn(doctor);
        ResultActions resultActions = mockMvc.perform(post("/admin/add-doctor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctorIsAddedByAdmin)));
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Tạo mới bác sĩ thành công"));
    }
    @Test
    public void ViewDetailPatient_SeeListAppointment_ReturnOk() throws Exception {
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);
        when(patientService.findByUserId(anyInt())).thenReturn(patient);
        when(appointmentService.findByPatient(any(Patient.class))).thenReturn(appointments);
        ResultActions resultActions = mockMvc.perform(get("/admin/appointment-detail-patient/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(appointments)));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(0));

    }
    @Test
    public void ViewDetailPatient_NotSeeListAppointment_ReturnNoContent() throws Exception {
        when(patientService.findByUserId(anyInt())).thenReturn(patient);
        when(appointmentService.findByPatient(any(Patient.class))).thenReturn(Collections.emptyList());
        ResultActions resultActions = mockMvc.perform(get("/admin/appointment-detail-patient/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Collections.emptyList())));
        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().string("Người dùng "+ patient.getUser().getName()+ " không có lịch khám"));
    }
    @Test
    public void ViewDetailDoctor_NoSeeListAppointment_ReturnNoContent() throws Exception{
        when(doctorService.findByUserId(anyInt())).thenReturn(doctor);
        when(appointmentService.findByDoctor(any(Doctor.class))).thenReturn(Collections.emptyList());
        ResultActions resultActions = mockMvc.perform(get("/admin/appointment-detail-doctor/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Collections.emptyList())));
        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().string("Bác sĩ "+doctor.getUser().getName()+" không có lịch khám"));
    }
    @Test
    public void ViewDetailDoctor_SeeListAppointment_ReturnOk() throws Exception{
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);
        when(doctorService.findByUserId(anyInt())).thenReturn(doctor);
        when(appointmentService.findByDoctor(any(Doctor.class))).thenReturn(appointments);
        ResultActions resultActions = mockMvc.perform(get("/admin/appointment-detail-doctor/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(appointments)));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(0));
    }
}
