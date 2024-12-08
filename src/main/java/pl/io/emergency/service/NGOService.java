package pl.io.emergency.service;

import org.springframework.stereotype.Service;
import pl.io.emergency.entity.Invitation;
import pl.io.emergency.entity.NGO;
import pl.io.emergency.entity.Volunteer;
import pl.io.emergency.repository.InvitationRepository;
import pl.io.emergency.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NGOService {

    private final UserRepository userRepository;
    private final InvitationRepository invitationRepository;
    public VolunteerService volunteerService;

    public NGOService(UserRepository userRepository, InvitationRepository invitationRepository) {
        this.userRepository = userRepository;
        this.invitationRepository = invitationRepository;
    }

    public List<NGO> getAllNGOs() {
        return userRepository.findByType(NGO.class);
    }

    public List<Volunteer> getVolunteerList(Long ngoId) {
        NGO ngo = userRepository.findById(ngoId)
                .filter(entity -> entity instanceof NGO)
                .map(entity -> (NGO) entity)
                .orElseThrow(() -> new IllegalArgumentException("NGO with ID " + ngoId + " does not exist."));

        return userRepository.findByType(Volunteer.class).stream()
                .filter(volunteer -> ngoId.equals(volunteer.getOrganizationId()))
                .collect(Collectors.toList());
    }

    public Invitation invite(Long ngoId, int eventId) {
        NGO ngo = userRepository.findById(ngoId)
                .filter(entity -> entity instanceof NGO)
                .map(entity -> (NGO) entity)
                .orElseThrow(() -> new IllegalArgumentException("NGO with ID " + ngoId + " does not exist."));

        List<String> volunteerEmails = getVolunteerList(ngoId).stream()
                .map(Volunteer::getEmail)
                .collect(Collectors.toList());

        Invitation invitation = new Invitation();
        invitation.setTitle("Invitation to Event " + eventId);
        invitation.setDescription("Join us for an important event!");
        invitation.setDate(new Date());
        invitation.setLink("http://example.com/event/" + eventId);
        invitation.setSender(ngo.getName());
        invitation.setReceivers(volunteerEmails);

        return invitationRepository.save(invitation);
    }

    public void markVolunteer(Long volunteerId, Long actionId, float rating) {
        Volunteer volunteer = userRepository.findById(volunteerId)
                .filter(entity -> entity instanceof Volunteer)
                .map(entity -> (Volunteer) entity)
                .orElseThrow(() -> new IllegalArgumentException("Volunteer with ID " + volunteerId + " does not exist."));

        volunteerService.getMarked(volunteer, actionId, rating);
    }

    private float calculateAverageRating(long volunteerId) {
        // Example logic for calculating the average
        // This would typically interact with a repository or another service
        return 0.0f; // Replace with actual logic
    }
}
