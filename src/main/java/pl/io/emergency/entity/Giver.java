package pl.io.emergency.entity;

import jakarta.persistence.Entity;
import lombok.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
public class Giver extends User {
    private String firstName;
    private String lastName;
    private Date birthDate;
}