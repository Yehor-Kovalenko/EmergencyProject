package pl.io.emergency.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.io.emergency.entity.ResourceStatus;
import pl.io.emergency.entity.ResourceType;

import java.time.LocalDate;

import java.io.Serializable;

//data transfer object
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDto implements Serializable{
    private Long id;
    private ResourceType type;
    private String description;
    private double amount;
    private LocalDate date;
    private ResourceStatus status;
    private Long destinationId;
    private Long holderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
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

    public ResourceStatus getStatus() {
        return status;
    }

    public void setStatus(ResourceStatus status) {
        this.status = status;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public Long getHolderId() {
        return holderId;
    }

    public void setHolderId(Long holderId) {
        this.holderId = holderId;
    }


    public ResourceDto (ResourceType type, String description, double amount, Long destinationId, Long holderId)
    {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.status = ResourceStatus.REGISTERED;
        this.destinationId = destinationId;
        this.holderId = holderId; //user_id
    }

    public ResourceDto (ResourceType type, String description, double amount, Long holderId)
    {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.status = ResourceStatus.REGISTERED;
        this.destinationId = null;
        this.holderId = holderId; //przekazywany pzrze uzytlownika
    }

}
