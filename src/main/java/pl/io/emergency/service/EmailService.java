package pl.io.emergency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private MailSender mailSender;

    public void sendEmail(SimpleMailMessage email) {
        this.mailSender.send(email);
    }
}
