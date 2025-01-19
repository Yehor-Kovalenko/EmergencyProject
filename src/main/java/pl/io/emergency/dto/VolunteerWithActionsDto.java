package pl.io.emergency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.io.emergency.entity.Action;
import pl.io.emergency.entity.users.Volunteer;

import java.util.List;

@Data
@AllArgsConstructor
public class VolunteerWithActionsDto {
    private Volunteer volunteer;
    private List<Action> actions;
}