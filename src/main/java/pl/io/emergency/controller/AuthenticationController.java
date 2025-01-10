package pl.io.emergency.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.io.emergency.dto.AuthRequestDto;
import pl.io.emergency.dto.RegistrationUserDto;
import pl.io.emergency.service.AuthenticationService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService userService) {
        this.authService = userService;
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
            authService.registerUser(dto);
            log.info("User registered successfully");
            return ResponseEntity.ok("User registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Authenticate a user", description = "Authenticate a user with provided credentials")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User authenticated"),
    })
    @PostMapping("/login")
    @Tag(name = "User login", description = "Operations related to user authentication")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid AuthRequestDto user) {
        try {
            Map<String, String> tokens = authService.loginUser(user);
            log.info("User authenticated");
            return ResponseEntity.ok(tokens);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Refresh user access token", description = "Refresh access token based on refresh token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Access token refreshed"),
    })
    @PostMapping("/refresh")
    @Tag(name = "Refresh token", description = "Operations related to refreshing token")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> refreshRequest) {
        try {
            Map<String, String> tokens = authService.refreshAccessToken(refreshRequest.get("refreshToken"));
            return ResponseEntity.ok(tokens);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
