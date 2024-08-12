package com.assignment.asm03.service;

import com.assignment.asm03.entity.User;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface EmailService {

    void sendEmail(User user) throws MessagingException, UnsupportedEncodingException;
    void sendEmailWithAttachment(User user, String pathFile) throws IOException, MessagingException;

}
