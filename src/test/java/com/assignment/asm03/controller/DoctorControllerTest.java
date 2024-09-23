package com.assignment.asm03.controller;

import com.assignment.asm03.entity.*;
import com.assignment.asm03.security.CustomUserDetail;
import com.assignment.asm03.security.JwtDecoder;
import com.assignment.asm03.security.JwtToPrincipalConverter;
import com.assignment.asm03.service.AppointmentService;
import com.assignment.asm03.service.EmailService;
import com.assignment.asm03.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DoctorController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class DoctorControllerTest {
    @MockBean
    private EmailService emailService;
    @MockBean
    private UserService userService;
    @MockBean
    private AppointmentService appointmentService;
    @MockBean
    private JwtDecoder jwtDecoder;
    @MockBean
    private JwtToPrincipalConverter jwtToPrincipalConverter;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private List<Appointment> appointments;
    private List<Patient> patients;
    @BeforeEach
    public void setUp(){
        appointments = new ArrayList<>();
        patients = new ArrayList<>();
        Hospital hospital = new Hospital("Cho Ray", "Sai Gon", "0693125741", 15, "good", new Date());

        Specialization specialization = new Specialization("pediatrician", "providing attentive care", 6, 50000, new Date());

        User user = new User("Le Tam","Female","letam@gmail.com","0974153268","Đống đa","123456",new Role(3,"DOCTOR"),true);

        MedicalHistory medicalHistory = new MedicalHistory("ho", "tri cam", "khong uong da lanh", "25/09/2023");
        User user1 = new User("Nguyen Nam", "Male", "nguyennam1998@gmail.com", "0985147632", "Sài Gòn", "test123", new Role(1,"USER"), true);
        Patient patient1 = new Patient(user1, medicalHistory);

        MedicalHistory medicalHistory2 = new MedicalHistory("ho", "khong cam", "uong nhieu nuoc", "26/09/2023");
        User user2 = new User("Tran Van A", "Male", "tranvana@gmail.com", "0912345678", "Hà Nội", "password123", new Role(1, "USER"), true);
        Patient patient2 = new Patient(user2, medicalHistory2);

        MedicalHistory medicalHistory3 = new MedicalHistory("khong ho", "cam", "khong uong ruou", "27/09/2023");
        User user3 = new User("Le Thi B", "Female", "lethib@gmail.com", "0976543210", "Đà Nẵng", "mypassword", new Role(1, "USER"), true);
        Patient patient3 = new Patient(user3, medicalHistory3);

        Doctor doctor = new Doctor("pediatrician", "medical university", "good student", hospital, specialization, user);
        user.setDoctor(doctor);
        doctor.setId(1);
        Appointment appointment1 = new Appointment("27/08/2024", "500000", "ho", doctor, patient1);
        Appointment appointment2 = new Appointment("27/08/2024", "600000", "ho", doctor, patient2);
        Appointment appointment3 = new Appointment("27/08/2024", "700000", "ho", doctor, patient3);

        appointments.add(appointment1);
        appointments.add(appointment2);
        appointments.add(appointment3);

        patients.add(patient1);
        patients.add(patient2);
        patients.add(patient3);

        Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(doctor.getUser().getRole().getName()));
        CustomUserDetail customUserDetail = new CustomUserDetail(doctor.getUser(),grantedAuthorities);

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(customUserDetail, null));
        SecurityContextHolder.setContext(securityContext);
    }
    @Test
    public void DoctorController_ViewPatients_ReturnOk() throws Exception {
        when(appointmentService.findByDoctor(any(Doctor.class))).thenReturn(appointments);
        ResultActions resultActions = mockMvc.perform(get("/doctor/list-patient")
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[0].user.name").value("Nguyen Nam"));
    }
    @Test
    public void DoctorController_ViewAppointments_ReturnOk() throws Exception{
        when(appointmentService.findByDoctor(any(Doctor.class))).thenReturn(appointments);
        ResultActions resultActions = mockMvc.perform(get("/doctor/list-appointment")
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$.[0].medicalFee").value("500000"));
    }
    @Test
    public void DoctorController_AcceptAppointment_ReturnBadRequest() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setId(10);
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        when(appointmentService.findById(anyInt())).thenReturn(appointment);
        ResultActions resultActions = mockMvc.perform(put("/doctor/accept-appointment/1")
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isBadRequest())
                .andExpect(content().string("Vui lòng nhập ID Appointment tương ứng với doctor"));
    }
    @Test
    public void DoctorController_AcceptAppointment_ReturnOk() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setId(1);
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        when(appointmentService.findById(anyInt())).thenReturn(appointment);
        when(appointmentService.save(any(Appointment.class))).thenAnswer(invocation -> {
            Appointment saveAppointment = invocation.getArgument(0);
            Assertions.assertTrue(saveAppointment.isConfirmByDoctor());
            return saveAppointment;
        });
        ResultActions resultActions = mockMvc.perform(put("/doctor/accept-appointment/1")
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk())
                .andExpect(content().string("Đã nhận lịch khám"));
    }
    @Test
    public void DoctorController_CancelAppointment_ReturnBadRequest() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setId(10);
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        when(appointmentService.findById(anyInt())).thenReturn(appointment);
        ResultActions resultActions = mockMvc.perform(put("/doctor/cancel-appointment/1")
                .param("reason","")
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isBadRequest())
                .andExpect(content().string("Vui lòng nhập ID Appointment tương ứng với doctor"));
    }
    @Test
    public void DoctorController_CancelAppointment_ReturnOk() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setId(1);
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        when(appointmentService.findById(anyInt())).thenReturn(appointment);
        when(appointmentService.save(any(Appointment.class))).thenAnswer(invocation -> {
            Appointment savedAppointment = invocation.getArgument(0);
            Assertions.assertFalse(savedAppointment.isConfirmByDoctor());
            Assertions.assertEquals("Baby à",savedAppointment.getReason());
            return savedAppointment;
        });
        ResultActions resultActions = mockMvc.perform(put("/doctor/cancel-appointment/1")
                .param("reason","Baby à")
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk())
                .andExpect(content().string("Đã hủy lịch khám"));
    }
    @Test
    public void DoctorController_SendPdf_ReturnNotFound() throws Exception {
        String email = "hello@gmail.com";
        String pathFile = "C:\\Program Files\\";
        when(userService.findByEmail(anyString())).thenReturn(null);
        ResultActions resultActions = mockMvc.perform(post("/doctor/send-pdf")
                .param("email", email)
                .param("pathFile", pathFile)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isNoContent())
                .andExpect(content().string("Không tìm thấy email: "+ email));
    }
    @Test
    public void DoctorController_SendPdf_ThrowException() throws Exception {
        String email = "hello@gmail.com";
        String pathFile = "C:\\Program Files\\";
        User user = new User("Le Tam","Female","letam@gmail.com","0974153268","Đống đa","123456",new Role(3,"DOCTOR"),true);
        when(userService.findByEmail(anyString())).thenReturn(user);
        doThrow(new MessagingException("Error sending email")).when(emailService).sendEmailWithAttachment(any(User.class),anyString());
        ResultActions resultActions = mockMvc.perform(post("/doctor/send-pdf")
                .param("email", email)
                .param("pathFile", pathFile)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isInternalServerError())
                .andExpect(content().string("Gửi mail không thành công \n Vui lòng nhập đúng email và đường dẫn file"));
    }
    @Test
    public void DoctorController_SendPdf_ReturnOk() throws Exception {
        String email = "hello@gmail.com";
        String pathFile = "C:\\Program Files\\";
        User user = new User("Le Tam","Female","letam@gmail.com","0974153268","Đống đa","123456",new Role(3,"DOCTOR"),true);
        when(userService.findByEmail(anyString())).thenReturn(user);
        doNothing().when(emailService).sendEmailWithAttachment(user,pathFile);
        ResultActions resultActions = mockMvc.perform(post("/doctor/send-pdf")
                .param("email", email)
                .param("pathFile", pathFile)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk())
                .andExpect(content().string("Gửi email thành công!"));
    }
}
