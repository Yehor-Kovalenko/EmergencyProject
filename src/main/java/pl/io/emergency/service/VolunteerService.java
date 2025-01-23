package pl.io.emergency.service;

import org.springframework.stereotype.Service;
import pl.io.emergency.entity.Action;
import pl.io.emergency.entity.users.Volunteer;
import pl.io.emergency.repository.ActionRepository;
import pl.io.emergency.repository.VolunteerRepository;

import java.util.List;

@Service
public class VolunteerService {

    private final ActionRepository actionRepository;
    private final VolunteerRepository volunteerRepository;

    public VolunteerService(ActionRepository actionRepository, VolunteerRepository volunteerRepository) {
        this.actionRepository = actionRepository;
        this.volunteerRepository = volunteerRepository;
    }

    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
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

    public void getMarked(Long volunteerId, int actionId, float rating) {
        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new IllegalArgumentException("Volunteer with ID " + volunteerId + " does not exist."));

        Action action = actionRepository.findById((long) actionId)
                .orElseThrow(() -> new IllegalArgumentException("Action with ID " + actionId + " does not exist."));

        if (!Integer.valueOf(String.valueOf(action.getVolunteerId())).equals(volunteer.getId().intValue())) {
            throw new IllegalStateException("Action does not belong to this volunteer.");
        }


        if (action.getRatingFromAction() != 0.0f) {
            throw new IllegalStateException("Action already has a rating.");
        }

        if (action.getAttendance()) {
            action.setRatingFromAction(rating);
            volunteer.setAvailable(true);
            actionRepository.save(action);
        } else {
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

    public List<Volunteer> getVolunteersByOrganizationId(Long organizationId) {
        return volunteerRepository.findByOrganizationId(organizationId);
    }

    public List<Action> getActionsByVolunteerId(Long volunteerId) {
        List<Action> history = actionRepository.findByVolunteerId(volunteerId);
        return history;
    }
}
