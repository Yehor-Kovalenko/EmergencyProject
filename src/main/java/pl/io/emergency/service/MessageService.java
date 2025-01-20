package pl.io.emergency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import pl.io.emergency.entity.MessageEntity;
import pl.io.emergency.entity.TemplateEntity;
import pl.io.emergency.entity.users.User;
import pl.io.emergency.repository.MessageRepository;
import pl.io.emergency.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final TemplateService templateService;
    private final UserRepository userRepository;
    @Autowired
    private  EmailService emailService;

    public MessageService(MessageRepository messageRepository, TemplateService templateService, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.templateService = templateService;
        this.userRepository = userRepository;
    }

    public boolean sendMessage(long senderId, long receiverId, String title, String body, String language) {
        MessageEntity message = new MessageEntity(senderId, receiverId, title, body, userRepository.findUsernameById(senderId), userRepository.findUsernameById(receiverId));
        int info = messageRepository.insertMessage(message);
        if (info == 1) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(userRepository.findEmailById(receiverId));
            mailMessage.setFrom("hum.aid.system@gmail.com");
            if (Objects.equals(language, "en")) {
                mailMessage.setSubject("📩 New Message in the Humanitarian Aid System! 🌟");
                mailMessage.setText("Hello!\n\nYou have a new message in the system. 📨\nCheck your inbox in the Humanitarian Aid System. 🌍");
            }
            else {
                mailMessage.setSubject("📩 Nowa wiadomość w systemie pomocy humanitarnej! 🌟");
                mailMessage.setText("Witaj!\n\nMasz nową wiadomość w systemie. 📨\nSprawdź swoją skrzynkę w systemie pomocy humanitarnej. 🌍");
            }
            try {
                emailService.sendEmail(mailMessage);
            } catch (Exception e) {
                //System.out.println("AWS ses send failed");
            }
            return true;
        }
        return false;
    }

    public List<MessageEntity> getMessages(long receiverId) {
        return messageRepository.read(receiverId);
    }

    public List<MessageEntity> getMessagesForSender(long senderId) {
        return messageRepository.readForSender(senderId);
    }

    public boolean sendNotification(String receiverEmail, String type, String language, Map<String, String> placeholders, Long receiverId) {
        TemplateEntity template = templateService.getTemplateByType(type, language);
        String title = templateService.renderTemplate(template.getTitle(), placeholders);
        String body = templateService.renderTemplate(template.getBody(), placeholders);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(receiverEmail);
        mailMessage.setFrom("hum.aid.system@gmail.com");
        mailMessage.setSubject(title);
        mailMessage.setText(body);
        try {
            emailService.sendEmail(mailMessage);
        } catch (Exception e) {
            //System.out.println("AWS ses send failed");
        }
        if (receiverId != null) {
            MessageEntity message = new MessageEntity(0, receiverId, title, body, "System pomocy humanitarnej", userRepository.findUsernameById(receiverId));
            messageRepository.insertMessage(message);
        }
        return true;
    }

    public List<User> searchUsers(String search) {
        return userRepository.searchUsersByName(search);
    }
}
