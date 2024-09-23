package com.assignment.asm03.controller;

import com.assignment.asm03.entity.Role;
import com.assignment.asm03.entity.User;
import com.assignment.asm03.security.JwtDecoder;
import com.assignment.asm03.security.JwtToPrincipalConverter;
import com.assignment.asm03.service.EmailService;
import com.assignment.asm03.service.UserService;
import jakarta.mail.MessagingException;
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

import java.io.UnsupportedEncodingException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(controllers = ForgotPasswordController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ForgotPasswordControllerTest {
    @MockBean
    private EmailService emailService;
    @MockBean
    private UserService userService;
    @MockBean
    private BCryptPasswordEncoder encode;
    @MockBean
    private JwtDecoder jwtDecoder;
    @MockBean
    private JwtToPrincipalConverter jwtToPrincipalConverter;
    @Autowired
    private MockMvc mockMvc;
    private User user;
    @BeforeEach
    public void setUp(){
        user = new User("Nguyen Nam", "Male", "nguyennam1998@gmail.com", "0985147632", "Sài Gòn", "test123", new Role("USER"), true);
    }
    @Test
    public void SendEmailForConfirmation_CanNotFindUser_ReturnNoFound() throws Exception {
        String email = "haha";
        when(userService.findByEmail(anyString())).thenReturn(null);
        ResultActions resultActions = mockMvc.perform(post("/forgot-password/email-confirmation")
                .param("email",email)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Không tìm thấy email: "+email));
    }
    @Test
    public void SendEmailForConfirmation_MessagingException_ReturnInternalServerError() throws Exception {
        String email = "haha";
        when(userService.findByEmail(anyString())).thenReturn(user);
        doThrow(new MessagingException()).when(emailService).sendEmail(any(User.class));
        ResultActions resultActions = mockMvc.perform(post("/forgot-password/email-confirmation")
                .param("email",email)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string("Gửi email không thành công"));
    }
    @Test
    public void SendEmailForConfirmation_UnsupportedEncodingException_ReturnInternalServerError() throws Exception {
        String email = "haha";
        when(userService.findByEmail(anyString())).thenReturn(user);
        doThrow(new UnsupportedEncodingException()).when(emailService).sendEmail(any(User.class));
        ResultActions resultActions = mockMvc.perform(post("/forgot-password/email-confirmation")
                .param("email",email)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().string("Gửi email không thành công"));
    }
    @Test
    public void SendEmailForConfirmation_SuccessSendingEmail_ReturnOk() throws Exception {
        String email = "";
        when(userService.findByEmail(anyString())).thenReturn(user);
        doNothing().when(emailService).sendEmail(any(User.class));
        ResultActions resultActions = mockMvc.perform(post("/forgot-password/email-confirmation")
                .param("email",email)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Gửi email thành công, vui lòng xem email"));
    }
    @Test
    public void ResetPassword_FailReset_ReturnBadRequest() throws Exception {
        String email = "hale@gmail.com";
        String newPassword = "hello";
        String confirmPassword = "hello1";
        when(userService.findByEmail(anyString())).thenReturn(user);
        when(userService.save(any(User.class))).thenReturn(user);
        ResultActions resultActions = mockMvc.perform(post("/forgot-password/reset-password")
                .param("email",email)
                .param("newPassword",newPassword)
                .param("confirmPassword",confirmPassword)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Mật khẩu chưa trùng khớp"));
    }
    @Test
    public void ResetPassword_SuccessReset_ReturnOk() throws Exception {
        String email = "hale@gmail.com";
        String newPassword = "hello";
        String confirmPassword = "hello";
        when(userService.findByEmail(anyString())).thenReturn(user);
        when(userService.save(any(User.class))).thenReturn(user);
        ResultActions resultActions = mockMvc.perform(post("/forgot-password/reset-password")
                .param("email",email)
                .param("newPassword",newPassword)
                .param("confirmPassword",confirmPassword)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Thay đổi mật khẩu thành công"));
    }
}
