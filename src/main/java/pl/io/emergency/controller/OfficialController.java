package pl.io.emergency.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.io.emergency.entity.users.Official;
import pl.io.emergency.service.OfficialService;
import java.util.List;

/**
 * Controller providing access to the API endpoints for managing Officials.
 */
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/officials")
@Tag(name = "Officials Management", description = "Operations related to Officials management")
public class OfficialController {

    private static final Logger log = LoggerFactory.getLogger(OfficialController.class);

    private final OfficialService officialService;

    public OfficialController(OfficialService officialService) {
        this.officialService = officialService;
        log.info("Official service instantiated");
    }

    @Operation(
            summary = "Get all Officials",
            description = "Retrieve a list of all Official."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of Officials"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @GetMapping
    public ResponseEntity<List<Official>> getAllOfficials() {
        log.info("Fetching all Officials");
        List<Official> officials = officialService.findAll();
        if (officials.isEmpty()) {
            log.info("No Officials found.");
            return ResponseEntity.ok(officials); // Zwrot pustej listy
        }
        return ResponseEntity.ok(officials);
    }




}
