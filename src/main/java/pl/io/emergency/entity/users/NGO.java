package pl.io.emergency.entity.users;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
public class NGO extends User {
    private String name;
    private String krs;

    public String getName() {
        return name;
    }

    public String getKrs() {
        return krs;
    }
}


