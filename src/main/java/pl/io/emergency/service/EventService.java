package pl.io.emergency.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.io.emergency.dto.HelpRequestDTORequest;
import pl.io.emergency.entity.Catastrophe;
import pl.io.emergency.entity.HelpRequest;
import pl.io.emergency.entity.HelpRequestStatus;
import pl.io.emergency.entity.users.Volunteer;
import pl.io.emergency.exception.CatastropheNotFound;
import pl.io.emergency.exception.HelpRequestNotFound;
import pl.io.emergency.repository.ActionRepository;
import pl.io.emergency.repository.CatastropheRepository;
import pl.io.emergency.repository.HelpRequestRepository;
import pl.io.emergency.repository.VolunteerRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private MessageService messageService;

    @Autowired
    private CatastropheRepository catastropheRepository;

    @Autowired
    private HelpRequestRepository helpRequestRepository;
    @Autowired
    private ActionRepository actionRepository;
    @Autowired
    private  VolunteerRepository volunteerRepository;

    public List<Catastrophe> getAllCatastrophes() {
        return catastropheRepository.findAll();
    }

    public Catastrophe createCatastrophe(Catastrophe catastrophe) {
        return catastropheRepository.save(catastrophe);
    }

    public HelpRequest createHelpRequest(Long catastropheId, @Valid HelpRequest helpRequest) {
        Optional<Catastrophe> catastropheOpt = catastropheRepository.findById(catastropheId);
        if (catastropheOpt.isPresent()) {
            Catastrophe catastrophe = catastropheOpt.get();
            helpRequest.setCatastrophe(catastrophe);

            HelpRequest savedHelpRequest =  helpRequestRepository.save(helpRequest);

            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("firstName", savedHelpRequest.getFirstName());
            placeholders.put("lastName", savedHelpRequest.getLastName());
            placeholders.put("description", savedHelpRequest.getDescription());
            placeholders.put("uniqueCode", savedHelpRequest.getUniqueCode());
            placeholders.put("status", savedHelpRequest.getStatus().toString());

            messageService.sendNotification(savedHelpRequest.getEmail(), "help1", savedHelpRequest.getEmailLanguage(), placeholders, null);

            return savedHelpRequest;
        } else {
            throw new CatastropheNotFound("Catastrophe not found with id " + catastropheId);
        }
    }

    public Optional<Catastrophe> getCatastropheById(long id) {
        return catastropheRepository.findById(id);
    }

    public Optional<HelpRequest> getHelpRequestByUniqueCode(String uniqueCode) {
        return helpRequestRepository.findByUniqueCode(uniqueCode);
    }

    public HelpRequest updateHelpRequest(String uniqueCode, @Valid HelpRequestDTORequest updateDTO) {
        HelpRequest helpRequest = helpRequestRepository.findByUniqueCode(uniqueCode)
                .orElseThrow(() -> new HelpRequestNotFound("HelpRequest not found with uniqueCode " + uniqueCode));

        helpRequest.setFirstName(updateDTO.getFirstName());
        helpRequest.setLastName(updateDTO.getLastName());
        helpRequest.setEmail(updateDTO.getEmail());
        helpRequest.setDescription(updateDTO.getDescription());

        HelpRequest savedHelpRequest =  helpRequestRepository.save(helpRequest);

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("firstName", savedHelpRequest.getFirstName());
        placeholders.put("lastName", savedHelpRequest.getLastName());
        placeholders.put("description", savedHelpRequest.getDescription());
        placeholders.put("uniqueCode", savedHelpRequest.getUniqueCode());
        placeholders.put("status", savedHelpRequest.getStatus().toString());

        messageService.sendNotification(savedHelpRequest.getEmail(), "help2", savedHelpRequest.getEmailLanguage(), placeholders, null);

        return savedHelpRequest;
    }

    public HelpRequest closeHelpRequest(String uniqueCode) {
        HelpRequest helpRequest = helpRequestRepository.findByUniqueCode(uniqueCode)
                .orElseThrow(() -> new HelpRequestNotFound("HelpRequest not found with uniqueCode " + uniqueCode));

        helpRequest.setStatus(HelpRequestStatus.CLOSED);
        return helpRequestRepository.save(helpRequest);
    }

    public Catastrophe closeCatastrophe(long id) {
        Catastrophe catastrophe = catastropheRepository.findById(id)
                .orElseThrow(() -> new CatastropheNotFound("Catastrophe not found with id " + id));
        List<Long> volunteerIds = actionRepository.findVolunteerIdsByCatastropheIdAndAttendanceTrue(id);
        System.out.println(volunteerIds);
        for (Long volunteerId : volunteerIds) {
            Optional<Volunteer> vol = volunteerRepository.findById(volunteerId);
            if (vol.isPresent()) {
                vol.get().setAvailable(true);
                vol.get().setReadyForMark(true);
//                System.out.println("ustawiam dostepnosc usera o id " + vol.get().getId() + " na " +vol.get().isAvailable());
//                System.out.println("ustawiam ready for mark usera o id " + vol.get().getId() + " na " +vol.get().isReadyForMark());
            }
        }
        catastrophe.setActive(false);
        return catastropheRepository.save(catastrophe);
    }
}