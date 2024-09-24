package com.assignment.asm03.service;

import com.assignment.asm03.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTests {
    @InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private MimeMessage mimeMessage;

    @Mock
    private MimeMessageHelper mimeMessageHelper;

    @Test
    public void EmailService_SendEmail_SendEmailSuccessfully() throws Exception {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");

        lenient().when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        lenient().when(mimeMessageHelper.getMimeMessage()).thenReturn(mimeMessage);

        doNothing().when(javaMailSender).send(mimeMessage);
        emailService.sendEmail(user);

        verify(javaMailSender, times(1)).send(mimeMessage);
    }
    @Test
    public void testSendEmailWithAttachment() throws MessagingException, IOException {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        String pathFile = "path/to/test/file.txt"; // Đường dẫn tệp giả

        lenient().when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        lenient().when(mimeMessageHelper.getMimeMessage()).thenReturn(mimeMessage);

        doNothing().when(javaMailSender).send(mimeMessage);
        // Gọi phương thức
        emailService.sendEmailWithAttachment(user, pathFile);

        // Kiểm tra xem phương thức gửi email đã được gọi hay chưa
        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
    }
}
