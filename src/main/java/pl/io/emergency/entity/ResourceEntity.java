package pl.io.emergency.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Klasa encji z automatycznie generowaną tabelą Resources z generowanym kluczem głównym i
 * dodatkową kolumną w bazie danych
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Resources")
public class ResourceEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resource", unique = true)
    private Long id;

    /*@ManyToOne // Określa relację wiele-do-jednego
    @JoinColumn(name = "destination", referencedColumnName = "id")
    private Catastrophe destination;*/

    /*@ManyToOne // Określa relację wiele-do-jednego
    @JoinColumn(name = "holderId", referencedColumnName = "id")
    private User holderId;*/
    //zaslepka
    @Column(name="destination")
    private Long destinationId;
    @Column(name="holderId")
    private Long holderId;
    //

    @Column(name="resource_type")
    private ResourceType resourceType;

    @Column(name = "resource_status")
    private ResourceStatus resourceStatus;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private double amount;

    @Column(name = "date_of_registration")
    private LocalDate date_of_registration;

    public ResourceEntity(ResourceType type, String description, double amount, Long destinationId, Long holderId) {
        this.resourceType = type;
        this.resourceStatus = ResourceStatus.REGISTERED;
        this.description = description;
        this.amount = amount;
        this.date_of_registration = LocalDate.now();
        this.destinationId = destinationId;
        this.holderId = holderId;
    }

    public ResourceEntity(ResourceType type, String description, double amount, Long holderId) {
        this.resourceType = type;
        this.resourceStatus = ResourceStatus.REGISTERED;
        this.description = description;
        this.amount = amount;
        this.date_of_registration = LocalDate.now();
        this.holderId = holderId;
    }
}

