package pl.io.emergency.service;

public interface EmailService {
    void sendEmail(String to, String subject, String content);
}