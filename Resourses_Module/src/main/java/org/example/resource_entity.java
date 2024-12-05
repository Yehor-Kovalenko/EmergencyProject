package org.example;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Klasa encji z automatycznie generowaną tabelą Resources z generowanym kluczem głównym i
 * dodatkową kolumną w bazie danych
 */
@Entity
@Table(name = "Resources")
public class resource_entity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resource", unique = true)
    private int id;

    @ManyToOne // Określa relację wiele-do-jednego
    @JoinColumn(name = "id_type", referencedColumnName = "id_type")
    private resources_types_entity id_type;

    @ManyToOne // Określa relację wiele-do-jednego
    @JoinColumn(name = "id_status", referencedColumnName = "id_status")
    private ResourcesStatusesEntity id_status;

    /*@ManyToOne // Określa relację wiele-do-jednego
    @JoinColumn(name = "destination", referencedColumnName = "x")
    private x destination;

    @ManyToOne // Określa relację wiele-do-jednego
    @JoinColumn(name = "holderId", referencedColumnName = "x")
    private x holderId;*/

    @Column(name = "description")
    private String description;
    @Column(name = "amount")
    private double amount;
    @Column(name = "date_of_registration")
    private LocalDate date_of_registration;

    public int getId() {
        return id;
    }

    public resources_types_entity getId_type() {
        return id_type;
    }

    public ResourcesStatusesEntity getId_status() {
        return id_status;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate_of_registration() {
        return date_of_registration;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_type(resources_types_entity id_type) {
        this.id_type = id_type;
    }

    public void setId_status(ResourcesStatusesEntity id_status) {
        this.id_status = id_status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate_of_registration(LocalDate date_of_registration) {
        this.date_of_registration = date_of_registration;
    }
}

