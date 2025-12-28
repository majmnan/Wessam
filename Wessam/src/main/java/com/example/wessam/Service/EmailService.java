package com.example.wessam.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    public void sendEmail(String toEmail,String subject,String body){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("alshahrani996655@gmail.com");
        message.setSubject(subject);
        message.setTo(toEmail);
        message.setText(body);
        mailSender.send(message);
    }
}