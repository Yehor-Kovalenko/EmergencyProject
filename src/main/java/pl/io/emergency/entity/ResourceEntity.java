package pl.io.emergency.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Klasa encji z automatycznie generowaną tabelą Resources z generowanym kluczem głównym i
 * dodatkową kolumną w bazie danych
 */
@Entity
@Table(name = "Resources")
public class ResourceEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resource", unique = true)
    private Long id;

    /*@ManyToOne // Określa relację wiele-do-jednego
    @JoinColumn(name = "id_type", referencedColumnName = "id_type")
    private ResourcesTypesEntity id_type;*/

    /*@ManyToOne // Określa relację wiele-do-jednego
    @JoinColumn(name = "id_status", referencedColumnName = "id_status")
    private ResourcesStatusesEntity id_status;*/

    /*@ManyToOne // Określa relację wiele-do-jednego
    @JoinColumn(name = "destination", referencedColumnName = "x")
    private x destination;

    @ManyToOne // Określa relację wiele-do-jednego
    @JoinColumn(name = "holderId", referencedColumnName = "x")
    private x holderId;*/

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

    public ResourceEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public ResourceStatus getResourceStatus() {
        return resourceStatus;
    }

    public void setResourceStatus(ResourceStatus resourceStatus) {
        this.resourceStatus = resourceStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate_of_registration() {
        return date_of_registration;
    }

    public void setDate_of_registration(LocalDate date_of_registration) {
        this.date_of_registration = date_of_registration;
    }

    public ResourceEntity(ResourceType type, String description, double amount, Long destinationId, Long holderId) {
        this.resourceType = type;
        this.resourceStatus = ResourceStatus.READY;
        this.description = description;
        this.amount = amount;
        this.date_of_registration = LocalDate.now();
    }

    public ResourceEntity(ResourceType type, String description, double amount, Long holderId) {
        this.resourceType = type;
        this.resourceStatus = ResourceStatus.READY;
        this.description = description;
        this.amount = amount;
        this.date_of_registration = LocalDate.now();
    }
}

