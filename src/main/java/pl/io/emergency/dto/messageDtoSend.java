package pl.io.emergency.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class messageDtoSend {

    private long senderId;
    private long receiverId;
    private String title;
    private String body;

    public messageDtoSend() {}

    public messageDtoSend(long senderId, long receiverId, String title, String body) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.title = title;
        this.body = body;
    }
}