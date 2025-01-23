package pl.io.emergency.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.io.emergency.dto.authorization.LoginRequestDto;
import pl.io.emergency.dto.authorization.LoginResponseDto;
import pl.io.emergency.dto.authorization.PasswordRequestDto;
import pl.io.emergency.dto.authorization.RegistrationRequestDto;
import pl.io.emergency.service.AuthenticationService;

import java.util.Map;
import java.util.Objects;

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
    public ResponseEntity<String> register(@RequestBody @Valid RegistrationRequestDto dto){
        try {
            authService.registerUser(dto);
            log.info("User registered successfully");
            return ResponseEntity.ok("User registered successfully");
        } catch (IllegalArgumentException e) {
            if (Objects.equals(e.getMessage(), "Username is already taken")) {
                return ResponseEntity.status(409).body("Username is already taken");
            } else if (Objects.equals(e.getMessage(), "Email is already taken")) {
                return ResponseEntity.status(409).body("Email is already taken");
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Authenticate a user", description = "Authenticate a user with provided credentials")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User authenticated"),
    })
    @PostMapping("/login")
    @Tag(name = "User login", description = "Operations related to user authentication")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto user, HttpServletResponse response) {
        try {
            if (!authService.loginUser(user)) {
                log.info("User login failed");
                return ResponseEntity.badRequest().body(null);
            }
            log.info("User authenticated successfully");

            String accessToken = authService.getAccessToken(user);
            ResponseCookie refreshToken = authService.getRefreshToken(user);
            LoginResponseDto loginResponseDto = authService.getLoginResponseDto(accessToken, user.getUsername());

            response.addHeader("Set-Cookie", refreshToken.toString());

            return ResponseEntity.ok().body(loginResponseDto);
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
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> refreshRequest) {
        try {
            Map<String, String> token = authService.refreshAccessToken(refreshRequest.get("refreshToken"));
            return ResponseEntity.ok().body(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
