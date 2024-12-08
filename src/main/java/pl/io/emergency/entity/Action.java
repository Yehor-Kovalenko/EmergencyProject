package pl.io.emergency.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "actions")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Action implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int actionId;

    private Long volunteerId;

    private float ratingFromAction;

    private Long catastropheId;

    private boolean attendance;

    public boolean getAttendance() {
        return attendance;
    }
}
