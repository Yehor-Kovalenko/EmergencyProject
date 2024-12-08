package pl.io.emergency.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "invitations")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Invitation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String title;

    private String description;

    private LocalDate date;

    private String link;

    @NotBlank
    private String sender;

    @ElementCollection
    private List<String> receivers;

    public String convert() {
        return String.format("Invitation: %s | Date: %s | Link: %s | From: %s",
                title, date, link, sender);
    }
}
