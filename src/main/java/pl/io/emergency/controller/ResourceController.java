package pl.io.emergency.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.io.emergency.service.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller providing access to the API endpoints for managing resources.
 */
@RestController
@RequestMapping("/api/resource")
@Tag(name = "Resource Management", description = "Operations related to resource management")
public class ResourceController {

    private static final Logger log = LoggerFactory.getLogger(ResourceController.class);
    private final Resource resourceService;

    public ResourceController(Resource resourceService) {
        this.resourceService = resourceService;
        log.info("Resource service instantiated");
    }

    @Operation(
            summary = "Create a new resource",
            description = "Allows adding a new resource with necessary details like type, description, amount, etc."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resource successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<?> createResource(@Valid @RequestBody Resource resourceRequest) {
        log.info("Received request to create a resource: {}", resourceRequest);

        // Business logic for processing the resource
        resourceService.setType(resourceRequest.getType());
        resourceService.setDescription(resourceRequest.getDescription());
        resourceService.setAmount(resourceRequest.getAmount());
        resourceService.setHolderId(resourceRequest.getHolderId());
        resourceService.setDate(resourceRequest.getDate());
        resourceService.setStatus(resourceRequest.getStatus());

        // Prepare response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Resource successfully created");
        response.put("status", resourceService.getStatus());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
