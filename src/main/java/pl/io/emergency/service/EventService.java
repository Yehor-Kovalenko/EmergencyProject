package pl.io.emergency.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.io.emergency.dto.HelpRequestDTORequest;
import pl.io.emergency.entity.Catastrophe;
import pl.io.emergency.entity.HelpRequest;
import pl.io.emergency.exception.CatastropheNotFound;
import pl.io.emergency.exception.HelpRequestNotFound;
import pl.io.emergency.repository.CatastropheRepository;
import pl.io.emergency.repository.HelpRequestRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private CatastropheRepository catastropheRepository;

    @Autowired
    private HelpRequestRepository helpRequestRepository;

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
            return helpRequestRepository.save(helpRequest);
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

        return helpRequestRepository.save(helpRequest);
    }
}