package pl.io.emergency.service;

import org.springframework.stereotype.Service;
import pl.io.emergency.entity.Action;
import pl.io.emergency.entity.Volunteer;
import pl.io.emergency.repository.ActionRepository;
import pl.io.emergency.repository.UserRepository;

import java.util.List;

@Service
public class VolunteerService {

    private final ActionRepository actionRepository;
    private final UserRepository userRepository;

    public VolunteerService(ActionRepository actionRepository, UserRepository userRepository) {
        this.actionRepository = actionRepository;
        this.userRepository = userRepository;
    }

    public List<Volunteer> getAllVolunteers() {
        return userRepository.findByType(Volunteer.class);
    }

    public float showAverage(Volunteer volunteer) {
        List<Action> history = actionRepository.findByVolunteerId(volunteer.getId());
        if (history == null || history.isEmpty()) {
            return 0.0f;
        }
        return (float) history.stream()
                .mapToDouble(Action::getRatingFromAction)
                .average()
                .orElse(0.0);
    }

    public void getMarked(Volunteer volunteer, Long actionId, float rating) {
        Action action = actionRepository.findById(actionId).orElse(null);

        if (action == null) {
            throw new IllegalArgumentException("Action with ID " + actionId + " does not exist.");
        }

        if (action.getVolunteerId() != volunteer.getId()) {
            throw new IllegalStateException("Action does not belong to this volunteer.");
        }

        if (action.getRatingFromAction() != 0.0f) {
            throw new IllegalStateException("Action already has a rating.");
        }

        if (action.getAttendance()) {
            action.setRatingFromAction(rating);

            actionRepository.save(action);
        }
        else {
            throw new IllegalArgumentException("Volunteer wasn't at this action.");
        }
    }

    public void acceptAttendance(Action action) {
        action.setAttendance(true);
        actionRepository.save(action);
    }

    public void rejectAttendance(Action action) {
        action.setAttendance(false);
        actionRepository.save(action);
    }
}
