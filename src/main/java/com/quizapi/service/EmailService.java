package com.quizapi.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);

    void sendHtmlEmail(String to, String subject, String body);

    void sendAttachmentsEmail(String to, String subject, String body);
}
