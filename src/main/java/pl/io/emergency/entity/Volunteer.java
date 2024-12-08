package pl.io.emergency.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
public class Volunteer extends User {

    private String firstName;
    private String lastName;
    private Date birthDate;
    private Long organizationId;

    private boolean available;
    private boolean readyForMark;


}
