package pl.io.emergency.service;

import org.springframework.stereotype.Service;
import pl.io.emergency.entity.Action;
import pl.io.emergency.repository.ActionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActionService {

    private final ActionRepository actionRepository;

    public ActionService(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    public List<Action> createActionsForVolunteers(List<Long> volunteerIds, long catastropheId) {
        List<Action> actions = new ArrayList<>();

        for (long volunteerId : volunteerIds) {
            Action action = new Action();
            action.setVolunteerId(volunteerId);
            action.setCatastropheId(catastropheId);
            action.setAttendance(false);
            action.setRatingFromAction(0.0f);

            Action savedAction = actionRepository.save(action);
            actions.add(savedAction);
        }

        return actions;
    }
}
