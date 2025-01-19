package pl.io.emergency.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "help_requests")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HelpRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String emailLanguage;

    @NotBlank
    private String description;

    private HelpRequestStatus status = HelpRequestStatus.REPORTED;

    @Column(unique = true, nullable = false, updatable = false)
    private String uniqueCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catastrophe_id")
    @JsonBackReference
    private Catastrophe catastrophe;

    private LocalDateTime reportedDate;

    @PrePersist
    protected void onCreate() {
        this.uniqueCode = UUID.randomUUID().toString();
        this.reportedDate = LocalDateTime.now();
    }
}

