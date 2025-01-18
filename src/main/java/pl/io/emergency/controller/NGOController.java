package pl.io.emergency.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.io.emergency.entity.Invitation;
import pl.io.emergency.entity.NGO;
import pl.io.emergency.entity.Volunteer;
import pl.io.emergency.service.NGOService;

import java.util.List;

/**
 * Controller providing access to the API endpoints for managing NGOs.
 */
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/ngo")
@Tag(name = "NGO Management", description = "Operations related to NGO management")
public class NGOController {

    private static final Logger log = LoggerFactory.getLogger(NGOController.class);

    private final NGOService ngoService;

    public NGOController(NGOService ngoService) {
        this.ngoService = ngoService;
        log.info("NGO service instantiated");
    }

    @Operation(
            summary = "Get all NGOs",
            description = "Retrieve a list of all NGOs."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of NGOs"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @GetMapping
    public ResponseEntity<List<NGO>> getAllNGOs() {
        log.info("Fetching all NGOs");
        List<NGO> ngos = ngoService.getAllNGOs();
        if (ngos.isEmpty()) {
            log.info("No NGOs found.");
            return ResponseEntity.ok(ngos); // Zwrot pustej listy
        }
        return ResponseEntity.ok(ngos);
    }


    @Operation(
            summary = "Get volunteers for an NGO",
            description = "Retrieve a list of volunteers associated with a specific NGO."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of volunteers"),
            @ApiResponse(responseCode = "404", description = "NGO not found")
    })
    @GetMapping("/{ngoId}/volunteers")
    public ResponseEntity<List<Volunteer>> getVolunteers(@PathVariable Long ngoId) {
        log.info("Fetching volunteers for NGO ID: {}", ngoId);
        List<Volunteer> volunteers = ngoService.getVolunteerList(ngoId);
        return ResponseEntity.ok(volunteers);
    }

    @Operation(
            summary = "Send an invitation to an event",
            description = "Send invitations to all volunteers of an NGO for a specific event."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully sent invitations"),
            @ApiResponse(responseCode = "404", description = "NGO not found")
    })
    @PostMapping("/{ngoId}/invite")
    public ResponseEntity<Invitation> sendInvitation(@PathVariable Long ngoId, @RequestParam int eventId) {
        log.info("Sending invitation for event ID: {} to NGO ID: {}", eventId, ngoId);
        Invitation invitation = ngoService.invite(ngoId, eventId);
        return ResponseEntity.ok(invitation);
    }

}
