package pl.io.emergency.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.io.emergency.entity.Action;
import pl.io.emergency.entity.users.Volunteer;
import pl.io.emergency.service.VolunteerService;

import java.util.List;

/**
 * Controller providing access to the API endpoints for managing Volunteers.
 */
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/volunteers")
@Tag(name = "Volunteer Management", description = "Operations related to managing volunteers")
public class VolunteerController {

    private static final Logger log = LoggerFactory.getLogger(VolunteerController.class);
    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @Operation(summary = "Get all volunteers", description = "Retrieve a list of all volunteers.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of volunteers")
    })
    @GetMapping
    public ResponseEntity<List<Volunteer>> getAllVolunteers() {
        log.info("Fetching all volunteers");
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        return ResponseEntity.ok(volunteers);
    }



    @Operation(summary = "Get actions by volunteer ID", description = "Retrieve a list of actions for a specific volunteer.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved actions"),
            @ApiResponse(responseCode = "404", description = "Volunteer not found")
    })
    @GetMapping("/{volunteerId}/actions")
    public ResponseEntity<List<Action>> getActionsByVolunteerId(@PathVariable Long volunteerId) {
        log.info("Fetching actions for volunteer ID: {}", volunteerId);
        List<Action> actions = volunteerService.getActionsByVolunteerId(volunteerId);
        return ResponseEntity.ok(actions);
    }

    @Operation(summary = "Mark volunteer", description = "Evaluate a specific volunteer based on their performance in an action.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully marked the volunteer"),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation failed"),
            @ApiResponse(responseCode = "404", description = "Volunteer or action not found")
    })
    @PostMapping("/{volunteerId}/mark")
    public ResponseEntity<Void> markVolunteer(@PathVariable Long volunteerId,
                                              @RequestParam int actionId,
                                              @RequestParam float rating) {
        log.info("Marking volunteer ID: {} for action ID: {} with rating {}", volunteerId, actionId, rating);
        volunteerService.getMarked(volunteerId, actionId, rating);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Accept attendance", description = "Mark an action as attended by the volunteer.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully marked attendance"),
            @ApiResponse(responseCode = "404", description = "Action not found")
    })
    @PostMapping("/{volunteerId}/actions/{actionId}/accept")
    public ResponseEntity<Void> acceptAttendance(@PathVariable Long volunteerId, @PathVariable Long actionId) {
        log.info("Accepting attendance for action ID: {} by volunteer ID: {}", actionId, volunteerId);
        Action action = volunteerService.getActionsByVolunteerId(volunteerId).stream()
                .filter(a -> a.getActionId() == actionId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Action not found for volunteer"));
        volunteerService.acceptAttendance(action);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Reject attendance", description = "Mark an action as not attended by the volunteer.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully rejected attendance"),
            @ApiResponse(responseCode = "404", description = "Action not found")
    })
    @PostMapping("/{volunteerId}/actions/{actionId}/reject")
    public ResponseEntity<Void> rejectAttendance(@PathVariable Long volunteerId, @PathVariable Long actionId) {
        log.info("Rejecting attendance for action ID: {} by volunteer ID: {}", actionId, volunteerId);
        Action action = volunteerService.getActionsByVolunteerId(volunteerId).stream()
                .filter(a -> a.getActionId() == actionId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Action not found for volunteer"));
        volunteerService.rejectAttendance(action);
        return ResponseEntity.ok().build();
    }
}
