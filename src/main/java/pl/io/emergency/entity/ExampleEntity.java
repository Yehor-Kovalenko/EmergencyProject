package pl.io.emergency.entity;


import jakarta.persistence.*;

import java.io.Serializable;

/**
 * Przykladowa klasa encji z automatycznie generowaną tabelą z generowanym kluczem głównym i dodatkową kolumną w bazie danych
 */
@Entity
@Table(name = "EXAMPLE")
public class ExampleEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "custom_name")
    private String name;
}

