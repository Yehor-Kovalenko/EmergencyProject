package pl.io.emergency.entity;

import jakarta.persistence.Entity;
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
}
