package pl.io.emergency.service;

import org.springframework.stereotype.Service;
import pl.io.emergency.entity.MessageEntity;
import pl.io.emergency.repository.MessageRepository;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public boolean sendMessage(long senderId, long receiverId, String title, String body) {
        MessageEntity message = new MessageEntity(senderId, receiverId, title, body);
        int info = messageRepository.insertMessage(message);
        return info == 1;
    }

    public List<MessageEntity> getMessages(long receiverId) {
        return messageRepository.read(receiverId);
    }
}
