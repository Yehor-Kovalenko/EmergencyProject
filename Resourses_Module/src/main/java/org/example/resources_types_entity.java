package org.example;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 * Klasa encji z automatycznie generowaną tabelą Resources_types z generowanym kluczem głównym i
 * dodatkową kolumną name w bazie danych
 */
@Entity
@Table(name = "Resources_types")
public class resources_types_entity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type", unique = true)
    private int id_type;

    @Column(name = "name")
    private String name;

    // Gettery i settery
    public int getIdType() {
        return id_type;
    }

    public void setIdType(int idType) {
        this.id_type = idType;
    }

    public String getTypeName() {
        return name;
    }

    public void setTypeName(String typeName) {
        this.name = typeName;
    }
}
