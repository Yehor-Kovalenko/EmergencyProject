package pl.io.emergency.entity.users;

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
public class Giver extends User {
    private String firstName;
    private String lastName;
    private Date birthDate;
}
