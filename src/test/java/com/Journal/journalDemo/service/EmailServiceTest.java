package com.Journal.journalDemo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void sendMailTest()
    {
        String to = "kavyapopat68@gmail.com";
        String subject = "Test Email";
        String body = "This is a test email sent from the EmailService.";
        emailService.sendMail(to, subject, body);
    }
}
