package pl.io.emergency.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

@Service
public class Resource {
    private int id;
    private String type;
    private String description;
    private double amount;
    private LocalDate date;
    private String status;
    private int destinationId;
    private int holderId;

    public boolean registerToDestination (String type, String description, double amount, int destinationId, int holderId)
    {
        return true;
    }

}
