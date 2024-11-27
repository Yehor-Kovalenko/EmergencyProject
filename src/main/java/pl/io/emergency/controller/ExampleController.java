package pl.io.emergency.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.io.emergency.entity.ExampleEntity;
import pl.io.emergency.service.ExampleService;

import java.util.HashMap;
import java.util.Map;

/**
 * Example implementation of the controller class that will provide access to the API endpoint of the backend server
 */
@RestController
@RequestMapping("/api/example")
public class ExampleController {
    private static final Logger log = LoggerFactory.getLogger(ExampleController.class);
    private final ExampleService exampleService;

    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
        log.info("Service instantiated");
    }

    /**
     * Example endpoint with the path /api/example/entity
     * Annotated using swagger and openapi specification so that documentation is created automatically
     * POST method, accepts ExampleEntity in JSON format as the request body of the HTTP request
     */
    @Operation(summary = "Example entity post method", description = "Detailed description why this method should be used, what does it do")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Example entity is valid"),
    })
    @Tag(name = "Example tag", description = "Operations related to example entity")
    @PostMapping("/entity")
    public ResponseEntity<?> accessExampleEndpoint(@RequestBody ExampleEntity exampleEntity) {
        log.info("Example entity has just been received ");
        Map<String, String> response = new HashMap<>();
        response.put("message", "Example good response");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}