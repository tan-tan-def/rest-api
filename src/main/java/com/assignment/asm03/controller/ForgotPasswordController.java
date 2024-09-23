package com.assignment.asm03.controller;

import com.assignment.asm03.entity.User;
import com.assignment.asm03.security.CustomUserDetail;
import com.assignment.asm03.service.EmailService;
import com.assignment.asm03.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forgot-password")
@Tag(name = "Forgot Password Controller", description = "Send email and change password")
public class ForgotPasswordController {

    private final EmailService emailService;
    private final UserService userService;
    private final BCryptPasswordEncoder encode;

    //5.1.3 ForgotPassword - Send email;
    @PostMapping("/email-confirmation")
    @Operation(summary = "5.1.3 Forgot Password - Send email", description = "Send email")
    public ResponseEntity<String> sendEmailForConfirmation(@RequestParam String email) {
        User user = userService.findByEmail(email);
        if(user==null){
            return new ResponseEntity<>("Không tìm thấy email: "+email, HttpStatus.NOT_FOUND);
        }
        try {
            emailService.sendEmail(user);
        } catch (MessagingException | UnsupportedEncodingException e) {
            return new ResponseEntity<>("Gửi email không thành công", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Gửi email thành công, vui lòng xem email", HttpStatus.OK);
    }

    //5.1.3 Forgot Password - Change Password
    @PostMapping("/reset-password")
    @Operation(summary = "5.1.3 Forgot Password - Change Password", description = "Change Password")
    public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String newPassword, @RequestParam String confirmPassword) {
        User user = userService.findByEmail(email);
        if (!newPassword.equals(confirmPassword)) {
            return new ResponseEntity<>("Mật khẩu chưa trùng khớp", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(encode.encode(newPassword));
        userService.save(user);
        return new ResponseEntity<>("Thay đổi mật khẩu thành công", HttpStatus.OK);
    }
}
