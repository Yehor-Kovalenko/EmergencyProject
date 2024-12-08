package pl.io.emergency.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
public class Official extends User {
    private String name;
    private String regon;
}
