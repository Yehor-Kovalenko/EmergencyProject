package pl.io.emergency.entity;


import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "REPORT")
public class Report implements Serializable {
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

    @Lob
    @Column(name = "data", columnDefinition = "TEXT")
    private String data;

    // Constructor for reports with dates
    public Report(LocalDateTime timestamp, LocalDateTime dateFrom, LocalDateTime dateTo, String data) {
        this.timestamp = timestamp;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.data = data;
    }

    // Constructor for reports without dates
    public Report(LocalDateTime timestamp, String data) {
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

