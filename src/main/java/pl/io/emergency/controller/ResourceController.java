package pl.io.emergency.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.io.emergency.dto.ResourceDto;
import pl.io.emergency.service.ResourceService;

import java.util.List;

/**
 * Controller providing access to the API endpoints for managing resources.
 */
@RestController
@RequestMapping("/api/resource")
@Tag(name = "Resource Management", description = "Operations related to resource management")
public class ResourceController {

    private static final Logger log = LoggerFactory.getLogger(ResourceController.class);
    //private final ResourceDto resourceDto;

    private final ResourceService resourceService;

    public ResourceController(/*ResourceDto resourceDto,*/ ResourceService resourceService) {
        //this.resourceDto = resourceDto;
        this.resourceService = resourceService;
        log.info("Resource service instantiated");
    }

    /*@Operation(
            summary = "Create a new resource",
            description = "Allows adding a new resource with necessary details like type, description, amount, etc."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resource successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })*/
    /*@PostMapping()
    public ResponseEntity<?> createResource(@Valid @RequestBody ResourceDto resourceDtoRequest) {
        log.info("Received request to create a resource: {}", resourceDtoRequest);

        // Business logic for processing the resource
        resourceDtoService.setType(resourceDtoRequest.getType());
        resourceDtoService.setDescription(resourceDtoRequest.getDescription());
        resourceDtoService.setAmount(resourceDtoRequest.getAmount());
        resourceDtoService.setHolderId(resourceDtoRequest.getHolderId());
        resourceDtoService.setDate(resourceDtoRequest.getDate());
        resourceDtoService.setStatus(resourceDtoRequest.getStatus());

        // Prepare response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Resource successfully created");
        response.put("status", resourceDtoService.getStatus());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }*/

    @Operation(
            summary = "Create a new resource to destination",
            description = "Allows adding a new resource with necessary details like type, description, amount, etc."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resource successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/destination")
    public ResponseEntity<ResourceDto> createResourceToDestination (@RequestParam(required = true) String type,
                                                                    @RequestParam(required = true) String description,
                                                                    @RequestParam(required = true) double amount,
                                                                    @RequestParam(required = true) Long destinationId,
                                                                    @RequestParam(required = true) Long holderId)
    {
        ResourceDto resourceDto = resourceService.CreateResourceToDestination(type, description, amount, destinationId, holderId);
        return ResponseEntity.ok(resourceDto);
    }

    @Operation(
            summary = "Create a new resource to donate",
            description = "Allows adding a new resource with necessary details like type, description, amount, etc."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resource successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/donate")
    public ResponseEntity<ResourceDto> createResourceToDonate (@RequestParam(required = true) String type,
                                                                    @RequestParam(required = true) String description,
                                                                    @RequestParam(required = true) double amount,
                                                                    @RequestParam(required = true) Long holderId)
    {
        ResourceDto resourceDto = resourceService.CreateResourceToDonate(type, description, amount, holderId);
        return ResponseEntity.ok(resourceDto);
    }

    //dodanie gdy dostep do zalogowanego
    /*@Operation(
            summary = "Create a new resource to warehouse",
            description = "Allows adding a new resource with necessary details like type, description, amount, etc."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resource successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/donate")
    public ResponseEntity<ResourceDto> createResourceToWarehouse (@RequestParam(required = true) String type,
                                                               @RequestParam(required = true) String description,
                                                               @RequestParam(required = true) double amount,
                                                               @RequestParam(required = true) Long holderId)
    {
        ResourceDto resourceDto = resourceService.CreateResourceToDonate(type, description, amount, /*current user const holderId);
        return ResponseEntity.ok(resourceDto);
    }*/

    @Operation(
            summary = "Get resources by holderId",
            description = "Fetches all resources associated with a specific holderId"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resources successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "No resources found for the given holderId")
    })
    @GetMapping("/getByholder/{holderId}")
    public ResponseEntity<List<ResourceDto>> getResourcesByHolderId(@PathVariable Long holderId) {
        log.info("Fetching resources for holderId: {}", holderId);

        // Wywołanie serwisowej metody findResourcesByHolderId
        List<ResourceDto> resources = resourceService.findResourcesByHolderId(holderId);

        if (resources.isEmpty()) {
            return ResponseEntity.notFound().build(); // Zwracamy 404, jeśli nie ma zasobów
        }

        return ResponseEntity.ok(resources); // Zwracamy 200 OK z listą zasobów
    }

    @Operation(
            summary = "Get resources by destinationId",
            description = "Fetches all resources associated with a specific destinationId"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resources successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "No resources found for the given destinationId")
    })
    @GetMapping("/getBydestination/{destinationId}")
    public ResponseEntity<List<ResourceDto>> getResourcesByDestinationId(@PathVariable Long destinationId) {
        log.info("Fetching resources for destinationId: {}", destinationId);

        // Wywołanie serwisowej metody findResourcesByDestinationId
        List<ResourceDto> resources = resourceService.findResourcesByDestinationId(destinationId);

        if (resources.isEmpty()) {
            return ResponseEntity.notFound().build(); // Zwracamy 404, jeśli nie ma zasobów
        }

        return ResponseEntity.ok(resources); // Zwracamy 200 OK z listą zasobów
    }
}
