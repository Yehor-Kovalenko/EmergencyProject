package pl.io.emergency.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        ResourceDto resourceDto = resourceService.CreateRespurceToDestination(type, description, amount, destinationId, holderId);
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
}
