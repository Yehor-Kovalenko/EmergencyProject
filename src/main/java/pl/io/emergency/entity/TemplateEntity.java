package pl.io.emergency.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;

@Entity
@Table(name = "Templates")
public class TemplateEntity implements Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Getter
    @Column(name = "title")
    private String title;

    @Getter
    @Column(name = "body")
    private String body;

}