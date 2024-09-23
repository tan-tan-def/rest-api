package com.assignment.asm03.controller;

import com.assignment.asm03.common.CurrencyFormatter;
import com.assignment.asm03.entity.*;
import com.assignment.asm03.security.CustomUserDetail;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private HospitalService hospitalService;
    @MockBean
    private SpecializationsService specializationsService;
    @MockBean
    private DoctorService doctorService;
    @MockBean
    private AppointmentService appointmentService;
    @MockBean
    private PatientService patientService;
    @MockBean
    private JwtDecoder jwtDecoder;
    @MockBean
    private JwtToPrincipalConverter jwtToPrincipalConverter;
    private List<Specialization> specializationList;
    private List<Hospital> hospitals;
    private Doctor doctorFinal;
    private Patient patientFinal;
    @BeforeEach
    public void init(){
        specializationList = new ArrayList<>();
        Specialization specialization1 = new Specialization("Cardiology", "Heart and blood vessels", 90, 300000, new Date());
        Specialization specialization2 = new Specialization("Neurology", "Nervous system and disorders", 110, 250000, new Date());
        Specialization specialization3 = new Specialization("Pediatrics", "Children's health", 150, 150000, new Date());
        Specialization specialization4 = new Specialization("Orthopedics", "Bones and joints", 170, 400000, new Date());
        Specialization specialization5 = new Specialization("Dermatology", "Skin and its diseases", 200, 180000, new Date());

        hospitals = new ArrayList<>();
        Hospital hospital1 = new Hospital("City Hospital", "123 Main St", "0123456789", 500, "General healthcare services", new Date());
        Hospital hospital2 = new Hospital("Green Valley Hospital", "456 Park Ave", "0987654321", 300, "Specialized in cardiology", new Date());
        Hospital hospital3 = new Hospital("Sunrise Medical Center", "789 Broadway", "0112233445", 200, "Pediatrics and maternity care", new Date());
        Hospital hospital4 = new Hospital("Oceanview Hospital", "321 Ocean Dr", "0223344556", 400, "Orthopedic treatments", new Date());
        Hospital hospital5 = new Hospital("Mountainview Clinic", "654 Hilltop Rd", "0334455667", 150, "Urgent care and general practice", new Date());

        User user = new User("Nguyen Nam", "Male", "nguyennam1998@gmail.com", "0985147632", "Sài Gòn", "test123", new Role("USER"), true);
        doctorFinal = new Doctor("pediatrician", "medical university", "good student", hospital1, specialization1, user);
        doctorFinal.setId(1);
        MedicalHistory medicalHistory = new MedicalHistory("Allergy to penicillin", "Avoid penicillin-based medications", "Patient should carry an allergy card", "2024-09-15");
        patientFinal = new Patient(user, medicalHistory);
        patientFinal.setId(1);
        user.setPatient(patientFinal);

        specializationList.add(specialization1);
        specializationList.add(specialization2);
        specializationList.add(specialization3);
        specializationList.add(specialization4);
        specializationList.add(specialization5);

        hospitals.add(hospital1);
        hospitals.add(hospital2);
        hospitals.add(hospital3);
        hospitals.add(hospital4);
        hospitals.add(hospital5);

        Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(doctorFinal.getUser().getRole().getName()));
        CustomUserDetail customUserDetail = new CustomUserDetail(doctorFinal.getUser(),grantedAuthorities);

//      Thiết lập SecurityContext để mô phỏng xác thực
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(customUserDetail, null));
        SecurityContextHolder.setContext(securityContext);

    }
    @Test
    public void UserController_ListOfSpecialization_ReturnNoContent() throws Exception {
        when(specializationsService.findAllAndSort()).thenReturn(Collections.emptyList());
        ResultActions resultActions = mockMvc.perform(get("/user/list-specialization")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Collections.emptyList())));
        resultActions
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().string("Không có chuyên khoa nào được tìm thấy"));
    }
    @Test
    public void UserController_ListOfSpecialization_ReturnOk() throws Exception {
        when(specializationsService.findAllAndSort()).thenReturn(specializationList);
        ResultActions resultActions = mockMvc.perform(get("/user/list-specialization")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(specializationList)));
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Cardiology"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Neurology"));
    }
    @Test
    public void UserController_ListOfHospital_ReturnNoContent() throws Exception{
        when(hospitalService.findAllAndSort()).thenReturn(Collections.emptyList());
        ResultActions resultActions = mockMvc.perform(get("/user/list-hospital")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Collections.emptyList())));
        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().string("Không có bệnh viện nào được tìm thấy"));
    }
    @Test
    public void UserController_ListOfHospital_ReturnOk() throws Exception{
        when(hospitalService.findAllAndSort()).thenReturn(hospitals);
        ResultActions resultActions = mockMvc.perform(get("/user/list-hospital")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hospitals)));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].phone").value("0334455667"));
    }
    @Test
    public void UserController_GetUser_ReturnOk() throws Exception{
        int patientId = 1;
        User user = new User("Nguyễn Văn A", "Nam", "nguyenvana@example.com", "0123456789", "123 Đường ABC, Quận 1", "securePassword123", new Role("USER"), true);
        MedicalHistory medicalHistory = new MedicalHistory("Allergy to penicillin", "Avoid penicillin-based medications", "Patient should carry an allergy card", "2024-09-15");
        Patient patient = new Patient(user, medicalHistory);
        patient.setId(patientId);
        user.setPatient(patient);

        when(patientService.findByPatientWithAppointments(patientId)).thenReturn(patient);

        ResultActions resultActions = mockMvc.perform(get("/user/show-user")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.medicalHistory.diagnosis").value("Allergy to penicillin"));;
    }
    @Test
    public void UserController_GeneralSearchHospital_ReturnNoContent() throws Exception {
        String location = "Hà Nội";
        String hospitalName = "Bệnh viện 2";

        when(hospitalService.findByNameAndAddress(hospitalName, location)).thenReturn(new ArrayList<>());

        ResultActions resultActions = mockMvc.perform(get("/user/search-general-hospital")
                .param("location", location)
                .param("hospital", hospitalName)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().string("Không tìm thấy bệnh viện " + hospitalName + " với vị trí " + location));
    }
    @Test
    public void UserController_GeneralSearchHospital_ReturnOk() throws Exception {
        String location = "Hà Nội";
        String hospitalName = "Bệnh viện 1";

        when(hospitalService.findByNameAndAddress(hospitalName, location)).thenReturn(hospitals);

        ResultActions resultActions = mockMvc.perform(get("/user/search-general-hospital")
                .param("location", location)
                .param("hospital", hospitalName)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void UserController_GeneralSearchSpecialization_ReturnsNoContent() throws Exception {
        String specialization = "a";
        int fee = 20000;

        when(specializationsService.findByNameAndFeeRange(specialization, fee - 1000, fee + 1000)).thenReturn(Collections.emptyList());

        ResultActions resultActions = mockMvc.perform(get("/user/search-general-specialization")
                .param("specialization", specialization)
                .param("fee", String.valueOf(fee))
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().string("Không tìm thấy chuyên khoa " + specialization + " với mức phí " + fee));
    }
    @Test
    public void generalSearchSpecialization_FeeTooLow_ReturnsBadRequest() throws Exception {
        String specialization = "Nội khoa";
        int fee = 5000;

        ResultActions resultActions = mockMvc.perform(get("/user/search-general-specialization")
                .param("specialization", specialization)
                .param("fee", String.valueOf(fee))
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Vui lòng nhập mệnh giá trên 10000"));
    }
    @Test
    public void GeneralSearchSpecialization_ValidFee_ReturnsOk() throws Exception {
        String specialization = "a";
        int fee = 200000;

        when(specializationsService.findByNameAndFeeRange(specialization, fee - 100000, fee + 100000)).thenReturn(specializationList);

        ResultActions resultActions = mockMvc.perform(get("/user/search-general-specialization")
                .param("specialization", specialization)
                .param("fee", String.valueOf(fee))
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Tìm kiếm thành công với chuyên khoa " + specialization + " và phí " + fee))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(5));
    }
    @Test
    public void listOfSpecializationSearch_NoResults_ReturnsNoContent() throws Exception {
        String valueToSearch = "không có chuyên khoa nào";

        when(specializationsService.searchSpecialization(valueToSearch)).thenReturn(Collections.emptyList());

        ResultActions resultActions = mockMvc.perform(post("/user/search-specialization")
                .param("valueToSearch", valueToSearch)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Không có tìm kiếm phù hợp với từ khóa: " + valueToSearch));
    }
    @Test
    public void listOfSpecializationSearch_ValidSearch_ReturnsOk() throws Exception {
        String valueToSearch = "o";

        when(specializationsService.searchSpecialization(valueToSearch)).thenReturn(Arrays.asList(
                specializationList.get(0),
                specializationList.get(1),
                specializationList.get(3),
                specializationList.get(4)
        ));

        ResultActions resultActions = mockMvc.perform(post("/user/search-specialization")
                .param("valueToSearch", valueToSearch)
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Danh sách các chuyên ngành cho từ khóa: " + valueToSearch))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(4));
    }
    @Test
    public void bookingAppointment_ValidInput_ReturnsCreated() throws Exception {
        String time = "2024-09-22T10:00:00";
        String reason = "Khám sức khỏe";
        int doctorId = 1;

        // Giả lập đối tượng Doctor
        when(doctorService.findByUserId(doctorId)).thenReturn(doctorFinal);

        // Giả lập hành vi lưu lịch hẹn
        String fee = CurrencyFormatter.formatCurrency(doctorFinal.getSpecialization().getFee(), "VND");
        Appointment appointment = new Appointment(time, fee, reason, doctorFinal, patientFinal);
        appointment.setNameDoctor(doctorFinal.getUser().getName());
        appointment.setNamePatient(patientFinal.getUser().getName());
        when(appointmentService.save(any(Appointment.class))).thenReturn(appointment);

        // Giả lập hành vi tăng số lượng đặt lịch
        when(hospitalService.save(any(Hospital.class))).thenReturn(hospitals.get(0));
        when(specializationsService.save(any(Specialization.class))).thenReturn(specializationList.get(0));

        ResultActions resultActions = mockMvc.perform(post("/user/booking-appointment")
                .param("time", time)
                .param("reason", reason)
                .param("doctor_id", String.valueOf(doctorId))
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$['Tên bệnh nhân: ']").value(patientFinal.getUser().getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$['Tên bác sĩ: ']").value(doctorFinal.getUser().getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$['Khung giờ khám: ']").value(time))
                .andExpect(MockMvcResultMatchers.jsonPath("$['Giá khám: ']").value(appointment.getMedicalFee()));
    }
}
