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
    private String language;

    public messageDtoSend() {}

    public messageDtoSend(long senderId, long receiverId, String title, String body, String language) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.title = title;
        this.body = body;
        this.language = language;
    }
}