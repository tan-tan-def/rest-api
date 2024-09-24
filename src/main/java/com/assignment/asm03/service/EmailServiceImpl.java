package com.assignment.asm03.service;

import org.springframework.core.io.FileSystemResource;
import com.assignment.asm03.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

@Service
//@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(User user) throws MessagingException, UnsupportedEncodingException {
        String passwordResetLink = "http://localhost:8080/swagger-ui/index.html#/Forgot%20Password%20Controller/resetPassword";
        String subject = "Change password";
        String senderName = "Wizardry Employment";
        String mailContent = "<p>Dear "+user.getName()+",</p>";
        mailContent += "Email verification successful, please click this link to change your password.";
        mailContent += "<a href=\"" + passwordResetLink + "\">Change Password</a></p>";
        mailContent += "<p>Thank you<br>Royal Center for Wizardry Employment</p>";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("phapthuathoanggia@gmail.com",senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);
        javaMailSender.send(message);
    }

    @Override
    public void sendEmailWithAttachment(User user, String pathFile) throws MessagingException, IOException  {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String subject = "Hồ sơ bệnh án";
        String senderName = "Bệnh viện Online";
        String mailContent = "<p>Chào anh/chị "+user.getName()+",</p>";
        mailContent+="Dưới đây là hồ sơ bệnh án, bệnh nhân vui lòng click vào để xem chi tiết";
        mailContent += "<p>Cảm ơn<br>Vui lòng liên hệ số 0995174632 để nhận sự hỗ trợ</p>";

        FileSystemResource fileSystemResource = new FileSystemResource(new File(pathFile));
        helper.addAttachment(Objects.requireNonNull(fileSystemResource.getFilename()), fileSystemResource);

        helper.setFrom("phapthuathoanggia@gmail.com",senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);

        javaMailSender.send(message);

    }
}
