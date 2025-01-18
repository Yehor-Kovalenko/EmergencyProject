package pl.io.emergency.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
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
    private LocalDate dateFrom;

    @Column(name = "dateTo")
    private LocalDate dateTo;

    @JsonIgnore
    @Lob
    @Column(name = "data", columnDefinition = "TEXT")
    private String databaseData;

    @Transient
    private List<T> data;

    @Enumerated(EnumType.STRING)
    @Column(name = "reportType")
    private ReportType reportType;

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    // Constructor for reports with dates
    public Report(ReportType reportType, LocalDateTime timestamp, LocalDate dateFrom, LocalDate dateTo, List<T> data) {
        this.timestamp = timestamp;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.data = data;
        this.reportType = reportType;
    }

    // Constructor for reports without dates
    public Report(LocalDateTime timestamp, List<T> data) {
        this.timestamp = timestamp;
        this.data = data;
        this.reportType = ReportType.GIVER_RESOURCES;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public String getDatabaseData() {
        return databaseData;
    }

    public void setDatabaseData(String databaseData) {
        this.databaseData = databaseData;
    }
}

