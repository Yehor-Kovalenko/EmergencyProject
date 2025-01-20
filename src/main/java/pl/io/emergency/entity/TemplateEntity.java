package pl.io.emergency.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Entity
@Table(name = "Templates")
public class TemplateEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    public TemplateEntity(String type, String language, String title, String body) {
        this.type = type;
        this.language = language;
        this.title = title;
        this.body = body;
    }

    public TemplateEntity() {}
}