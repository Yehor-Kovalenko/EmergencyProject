package pl.io.emergency.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.io.emergency.dto.RegistrationUserDto;
import pl.io.emergency.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class RegistrationController {
    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
        log.info("Service instantiated");
    }

    @Operation(summary = "Register new user", description = "Register new user with provided details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
    })
    @PostMapping("/register")
    @Tag(name = "User registration", description = "Operations related to user management")
    public ResponseEntity<String> register(@RequestBody @Valid RegistrationUserDto dto){
        try {
            userService.registerUser(dto);
            log.info("User registered successfully");
            return ResponseEntity.ok("User registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
