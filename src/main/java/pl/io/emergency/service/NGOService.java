package pl.io.emergency.service;

import org.springframework.stereotype.Service;
import pl.io.emergency.entity.Action;
import pl.io.emergency.entity.Invitation;
import pl.io.emergency.entity.users.NGO;
import pl.io.emergency.entity.users.Volunteer;
import pl.io.emergency.repository.InvitationRepository;
import pl.io.emergency.repository.NGORepository;
import pl.io.emergency.repository.VolunteerRepository;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NGOService {

    private final NGORepository ngoRepository;
    private final VolunteerRepository volunteerRepository;
    private final InvitationRepository invitationRepository;
    private final VolunteerService volunteerService;
    private final ActionService actionService;
    private final MessageService messageService;

    public NGOService(NGORepository ngoRepository,
                      VolunteerRepository volunteerRepository,
                      InvitationRepository invitationRepository,
                      VolunteerService volunteerService, ActionService actionService, MessageService messageService) {
        this.ngoRepository = ngoRepository;
        this.volunteerRepository = volunteerRepository;
        this.invitationRepository = invitationRepository;
        this.volunteerService = volunteerService;
        this.actionService = actionService;
        this.messageService = messageService;
    }

    // Pobiera wszystkie NGO
    public List<NGO> getAllNGOs() {
        return ngoRepository.findAll();
    }

    // Pobiera listę wolontariuszy dla danego NGO na podstawie organizationId
    public List<Volunteer> getVolunteerList(Long ngoId) {
        NGO ngo = ngoRepository.findById(ngoId)
                .orElseThrow(() -> new IllegalArgumentException("NGO with ID " + ngoId + " does not exist."));

        return volunteerRepository.findByOrganizationId(ngo.getId());
    }

    // Wysyła zaproszenia do wszystkich wolontariuszy przypisanych do NGO
    public Invitation invite(Long ngoId, int eventId) {
        NGO ngo = ngoRepository.findById(ngoId)
                .orElseThrow(() -> new IllegalArgumentException("NGO with ID " + ngoId + " does not exist."));

        List<String> volunteerEmails = getVolunteerList(ngoId).stream()
                .map(Volunteer::getEmail)
                .collect(Collectors.toList());

        List<Long> volunteerId = getVolunteerList(ngoId).stream()
                .map(Volunteer::getId)
                .collect(Collectors.toList());

        Invitation invitation = new Invitation();
        invitation.setTitle("Invitation to Event " + eventId);
        invitation.setDescription("Join us for an important event!");
        invitation.setDate(new Date());
        invitation.setLink("http://localhost:5173/event/" + eventId);
        invitation.setSender(ngo.getName());
        invitation.setReceivers(volunteerEmails);

        Map<String, String> placeholders = new HashMap<>();
//        placeholders.put("eventLink", "http://example.com/event/" + eventId);
        String dynamic_link = "";
        long vol_id = 0;
        long action_id = 0;
        List<Action> actions = actionService.createActionsForVolunteers(volunteerId, eventId);
        for (int i = 0; i < volunteerEmails.size(); i++) {
            String email = volunteerEmails.get(i);
            vol_id = volunteerId.get(i);
            action_id = actions.get(i).getActionId();
            dynamic_link = "http://localhost:5173/volunteers/" + vol_id + "/actions/" + action_id;
//            System.out.println(dynamic_link);
            placeholders.put("eventLink", dynamic_link);
            messageService.sendNotification(email, "vol1", "pl", placeholders, vol_id);
        }


        return invitationRepository.save(invitation);
    }

    // Ocenia wolontariusza
    public void markVolunteer(Long volunteerId, int actionId, float rating) {
        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new IllegalArgumentException("Volunteer with ID " + volunteerId + " does not exist."));

        volunteerService.getMarked(volunteer.getId(), actionId, rating);
    }

    // Przykładowa metoda do obliczania średniej oceny wolontariusza
    private float calculateAverageRating(Long volunteerId) {
        List<Action> actions = volunteerService.getActionsByVolunteerId(volunteerId);
        if (actions.isEmpty()) {
            return 0.0f;
        }
        return (float) actions.stream()
                .mapToDouble(Action::getRatingFromAction)
                .average()
                .orElse(0.0);
    }
}
