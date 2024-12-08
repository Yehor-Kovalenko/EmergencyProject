package pl.io.emergency.resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.io.emergency.entity.Invitation;
import pl.io.emergency.entity.NGO;
import pl.io.emergency.entity.Role;
import pl.io.emergency.entity.Volunteer;
import pl.io.emergency.repository.InvitationRepository;
import pl.io.emergency.repository.UserRepository;
import pl.io.emergency.service.NGOService;
import pl.io.emergency.service.VolunteerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NGOServiceTest {

    private UserRepository userRepository;
    private InvitationRepository invitationRepository;
    private VolunteerService volunteerService;
    private NGOService ngoService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        invitationRepository = mock(InvitationRepository.class);
        volunteerService = mock(VolunteerService.class);
        ngoService = new NGOService(userRepository, invitationRepository);
        ngoService.volunteerService = volunteerService; // ustawienie VolunteerService
    }

    @Test
    public void testGetAllNGOs() {
        // Arrange
        NGO ngo1 = new NGO();
        ngo1.setName("NGO 1");
        NGO ngo2 = new NGO();
        ngo2.setName("NGO 2");

        when(userRepository.findByType(NGO.class)).thenReturn(List.of(ngo1, ngo2));

        // Act
        List<NGO> ngos = ngoService.getAllNGOs();

        // Assert
        assertNotNull(ngos);
        assertEquals(2, ngos.size());
        assertEquals("NGO 1", ngos.get(0).getName());
    }

    @Test
    public void testGetVolunteerList() {
        // Arrange
        Long ngoId = 1L;
        NGO ngo = new NGO();
        ngo.setId(ngoId);
        ngo.setRole(Role.NGO);

        when(userRepository.findById(ngoId)).thenReturn(Optional.of(ngo));

        Volunteer volunteer1 = new Volunteer();
        volunteer1.setOrganizationId(ngoId);
        volunteer1.setEmail("volunteer1@example.com");

        Volunteer volunteer2 = new Volunteer();
        volunteer2.setOrganizationId(ngoId);
        volunteer2.setEmail("volunteer2@example.com");

        when(userRepository.findByType(Volunteer.class)).thenReturn(List.of(volunteer1, volunteer2));

        // Act
        List<Volunteer> volunteers = ngoService.getVolunteerList(ngoId);

        // Assert
        assertNotNull(volunteers);
        assertEquals(2, volunteers.size());
        assertEquals("volunteer1@example.com", volunteers.get(0).getEmail());
    }

    @Test
    public void testInvite() {
        // Arrange
        Long ngoId = 1L;
        int eventId = 100;
        NGO ngo = new NGO();
        ngo.setId(ngoId);
        ngo.setName("Test NGO");
        ngo.setRole(Role.NGO);

        when(userRepository.findById(ngoId)).thenReturn(Optional.of(ngo));

        Volunteer volunteer = new Volunteer();
        volunteer.setOrganizationId(ngoId);
        volunteer.setEmail("volunteer@example.com");

        when(userRepository.findByType(Volunteer.class)).thenReturn(List.of(volunteer));

        Invitation savedInvitation = new Invitation();
        savedInvitation.setId(1L);
        savedInvitation.setTitle("Invitation to Event " + eventId);

        when(invitationRepository.save(any(Invitation.class))).thenReturn(savedInvitation);

        // Act
        Invitation result = ngoService.invite(ngoId, eventId);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Invitation to Event 100", result.getTitle());
    }

    @Test
    public void testMarkVolunteer() {
        // Arrange
        Long volunteerId = 1L;
        Long actionId = 100L;
        float rating = 4.5f;

        Volunteer volunteer = new Volunteer();
        volunteer.setId(volunteerId);
        volunteer.setRole(Role.VOLUNTEER);

        when(userRepository.findById(volunteerId)).thenReturn(Optional.of(volunteer));

        // Act
        ngoService.markVolunteer(volunteerId, actionId, rating);

        // Assert
        verify(volunteerService, times(1)).getMarked(volunteer, actionId, rating);
    }

    @Test
    public void testGetVolunteerList_NonExistentNGO() {
        // Arrange
        Long ngoId = 1L;
        when(userRepository.findById(ngoId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ngoService.getVolunteerList(ngoId);
        });
        assertEquals("NGO with ID 1 does not exist.", exception.getMessage());
    }
}
