package pl.io.emergency.entity;
import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Messages")
public class MessageEntity implements Serializable{

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Getter
    @Column(name = "senderId", nullable = false)
    private long senderId;

    @Getter
    @Column(name = "receiverId", nullable = false)
    private long receiverId;

    @Getter
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime date;

    @Getter
    @Column(name = "title")
    private String title;

    @Getter
    @Column(name= "body")
    private String body;

    public MessageEntity() {

    }

    public MessageEntity(long senderId, long receiverId, String title, String body)
    {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.date = LocalDateTime.now();
        this.title = title;
        this.body = body;
    }
}
