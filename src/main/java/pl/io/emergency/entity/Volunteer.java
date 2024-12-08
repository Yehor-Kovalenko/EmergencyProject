package pl.io.emergency.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "volunteers")
public class Volunteer extends User {

    private String firstName;
    private String lastName;
    private Date birthDate;
    private Long organizationId;

    private boolean available;
    private boolean readyForMark;


}
