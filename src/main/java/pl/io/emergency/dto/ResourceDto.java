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

    public ResourceDto (ResourceType type, String description, double amount, Long destinationId, Long holderId)
    {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.status = ResourceStatus.READY;
        this.destinationId = destinationId;
        this.holderId = holderId; //user_id
    }

    public ResourceDto (ResourceType type, String description, double amount, Long holderId)
    {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.status = ResourceStatus.READY;
        this.destinationId = null;
        this.holderId = holderId; //przekazywany pzrze uzytlownika
    }

}
