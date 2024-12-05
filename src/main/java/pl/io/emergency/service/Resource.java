package pl.io.emergency.service;

import java.time.LocalDate;

import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class Resource {
    private String id;
    private String type;
    private String description;
    private double amount;
    private LocalDate date;
    private String status;

    private String destinationId;
    private String holderId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getHolderId() {
        return holderId;
    }

    public void setHolderId(String holderId) {
        this.holderId = holderId;
    }

    public Resource()
    {}

    public Resource (String type, String description, double amount, String destinationId, String holderId)
    {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.date =LocalDate.now();
        this.status = "0";
        this.destinationId = destinationId;
        this.holderId = holderId; //user_id
    }

    public Resource (String type, String description, double amount, String holderId)
    {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.date =LocalDate.now();
        this.status = "0";
        this.destinationId = null;
        this.holderId = holderId; //przekazywany pzrze uzytlownika
    }

}
