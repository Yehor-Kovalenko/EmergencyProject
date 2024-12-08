package pl.io.emergency.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import java.util.List;

@Entity
@Table(name = "REPORT")
public class Report<T> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "dateFrom")
    private LocalDateTime dateFrom;

    @Column(name = "dateTo")
    private LocalDateTime dateTo;

    @JsonIgnore
    @Lob
    @Column(name = "data", columnDefinition = "TEXT")
    private String databaseData;

    @Transient
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    // Constructor for reports with dates
    public Report(LocalDateTime timestamp, LocalDateTime dateFrom, LocalDateTime dateTo, List<T> data) {
        this.timestamp = timestamp;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.data = data;
    }

    // Constructor for reports without dates
    public Report(LocalDateTime timestamp, List<T> data) {
        this.timestamp = timestamp;
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }

    public String getDatabaseData() {
        return databaseData;
    }

    public void setDatabaseData(String databaseData) {
        this.databaseData = databaseData;
    }
}

