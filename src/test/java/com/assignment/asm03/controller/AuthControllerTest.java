package com.assignment.asm03.controller;

import com.assignment.asm03.entity.*;
import com.assignment.asm03.model.LoginResponse;
import com.assignment.asm03.model.UserDTO;
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

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @MockBean
    private AuthService authService;
    @MockBean
    private BCryptPasswordEncoder encoder;
    @MockBean
    private UserService userService;
    @MockBean
    private RoleService roleService;
    @MockBean
    private PatientService patientService;
    @MockBean
    private BasicConditionService basicConditionService;
    @MockBean
    private MedicalHistoryService medicalHistoryService;
    @MockBean
    private JwtDecoder jwtDecoder;
    @MockBean
    private JwtToPrincipalConverter jwtToPrincipalConverter;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private UserDTO userDTO;
    private  User user1;
    private Patient patient1;
    private BasicCondition basicCondition;
    private MedicalHistory medicalHistory;
    @BeforeEach
    public void setUp(){
        userDTO = new UserDTO("hà le","Male","nhata@gmail.com","069","dfdf","dfdfdf","dfdfdf");
        medicalHistory = new MedicalHistory("ho", "tri cam", "khong uong da lanh", "25/09/2023");
        user1 = new User("Nguyen Nam", "Male", "nguyennam1998@gmail.com", "0985147632", "Sài Gòn", "test123", new Role(1,"USER"), true);
        patient1 = new Patient(user1, medicalHistory);
        basicCondition = new BasicCondition(1, "Diabetes", "A chronic condition that affects how the body processes blood sugar.", "Insulin therapy", "High");
        basicCondition.setPatient(patient1);

    }
    @Test
    public void AuthController_LoginSuccess_ReturnOk() throws Exception {
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setEmail(email);
        user.setName("Nguyen Van A");
        user.setActive(true);
        String token = "mocked-token";

        when(userService.findByEmail(email)).thenReturn(user);
        when(authService.attemptLogin(email, password)).thenReturn(new LoginResponse(token));

        mockMvc.perform(post("/auth/login")
                        .param("email", email)
                        .param("password", password)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token))
                .andExpect(jsonPath("$.message").value("Đăng nhập thành công"));
    }
    @Test
    public void Login_UserNotActive_ReturnForbidden() throws Exception {
        // Thiết lập dữ liệu giả
        User user = new User();
        user.setActive(false);
        user.setDescription("Tài khoản bị khóa do vi phạm chính sách.");

        when(userService.findByEmail(anyString())).thenReturn(user);

        ResultActions resultActions = mockMvc.perform(post("/auth/login")
                        .param("email", "test@example.com")
                        .param("password", "password123")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED));
        resultActions.andExpect(status().isForbidden())
                .andExpect(content().json("{\"Message: \":\"Tài khoản của bạn đã bị khóa.\",\"Lí do: \":\"Tài khoản bị khóa do vi phạm chính sách.\"}"));
    }

    @Test
    public void AuthController_SignUp_ReturnBadRequest() throws Exception {
        userDTO.setEmail("fd");

        ResultActions resultActions = mockMvc.perform(post("/auth/sign-up")
                .content(objectMapper.writeValueAsString(userDTO))
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void AuthController_SignUp_ReturnCreated() throws Exception {
        when(roleService.findById(anyInt())).thenReturn(new Role(1, "USER"));
        when(userService.save(any(User.class))).thenReturn(user1);
        when(patientService.save(any(Patient.class))).thenReturn(patient1);
        when(basicConditionService.findAll()).thenReturn(Arrays.asList(basicCondition));
        when(basicConditionService.findById(anyInt())).thenReturn(basicCondition);
        when(medicalHistoryService.findAll()).thenReturn(Arrays.asList(medicalHistory));
        when(medicalHistoryService.findById(anyInt())).thenReturn(medicalHistory);

        ResultActions resultActions = mockMvc.perform(post("/auth/sign-up")
                .content(objectMapper.writeValueAsString(userDTO))
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isCreated())
                .andExpect(content().string("Đăng kí thành công"));
    }
}
