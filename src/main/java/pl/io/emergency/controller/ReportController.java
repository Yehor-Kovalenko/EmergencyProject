package pl.io.emergency.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.io.emergency.entity.ExampleEntity;
import pl.io.emergency.entity.Giver;
import pl.io.emergency.service.ExampleService;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller managing API endpoints for generating reports about giver, resources, emergencies, history, etc.
 */
@RestController
@RequestMapping("/api/report")
public class ReportController {
    private static final Logger log = LoggerFactory.getLogger(ReportController.class);
    private final ExampleService exampleService;

    public ReportController(ExampleService exampleService) {
        this.exampleService = exampleService;
        log.info("Service instantiated");
    }


    @Operation(summary = "Generate report for a Giver", description = "Creates a report containing information about donations a specific Giver has made," +
            " and saves it in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Report created, returned, saved."),
    })
    @Tag(name = "Report Generation", description = "Operations related to generating reports")
    @PostMapping("/giver")
    public ResponseEntity<?> getGiverReport(@RequestParam(required = true) long giverId) {
        log.info("Giver report requested with id {}", giverId);
        Map<String, String> response = new HashMap<>();
        String messageResponse = "You passed id: " + giverId;
        response.put("message", messageResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
