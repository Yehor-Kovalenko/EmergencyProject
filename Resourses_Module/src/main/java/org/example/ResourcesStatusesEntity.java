package org.example;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 * Klasa encji z automatycznie generowaną tabelą Resources_statusess z generowanym kluczem głównym i
 * dodatkową kolumną name w bazie danych
 */
@Entity
@Table(name = "Resources_statuses")
public class ResourcesStatusesEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status", unique = true)
    private int id_status;

    @Column(name = "name")
    private String name;

    // Gettery i settery
    public int getIdStatus() {
        return id_status;
    }

    public void setIdStatus(int idStatus) {
        this.id_status = idStatus;
    }

    public String getStatusName() {
        return name;
    }

    public void setStatusName(String statusName) {
        this.name = statusName;
    }
}
