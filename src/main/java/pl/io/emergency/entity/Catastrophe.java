package pl.io.emergency.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "catastrophes")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Catastrophe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String type;

    private double longitude;
    private double latitude;

    @OneToMany(mappedBy = "catastrophe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<HelpRequest> helpRequests;

    private boolean isActive;

    private LocalDateTime reportedDate;

    @PrePersist
    protected void onCreate() {
        reportedDate = LocalDateTime.now();
    }
}
