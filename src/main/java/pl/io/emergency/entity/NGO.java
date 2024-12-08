package pl.io.emergency.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
public class NGO extends User {
    private String name;
    private String krs;
}


