package pl.io.emergency.service;

import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.io.emergency.dto.AuthRequestDto;
import pl.io.emergency.dto.RegistrationUserDto;
import pl.io.emergency.entity.users.*;
import pl.io.emergency.repository.UserRepository;
import pl.io.emergency.security.CookieUtil;
import pl.io.emergency.security.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    public AuthenticationService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil, CookieUtil cookieUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.cookieUtil = cookieUtil;
    }

    public void registerUser(RegistrationUserDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())
                && userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Username is already taken");
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        User user = switch (dto.getRole().toUpperCase()) {
            case "GIVER" -> Giver.builder()
                    .username(dto.getUsername())
                    .password(encodedPassword)
                    .email(dto.getEmail())
                    .phone(dto.getPhone())
                    .role(Role.GIVER)
                    .firstName(dto.getFirstName())
                    .lastName(dto.getLastName())
                    .birthDate(dto.getBirthDate())
                    .build();
            case "VOLUNTEER" -> Volunteer.builder()
                    .username(dto.getUsername())
                    .password(encodedPassword)
                    .email(dto.getEmail())
                    .phone(dto.getPhone())
                    .role(Role.VOLUNTEER)
                    .firstName(dto.getFirstName())
                    .lastName(dto.getLastName())
                    .birthDate(dto.getBirthDate())
                    .organizationId(dto.getOrganizationId())
                    .build();
            case "NGO" -> NGO.builder()
                    .username(dto.getUsername())
                    .password(encodedPassword)
                    .email(dto.getEmail())
                    .phone(dto.getPhone())
                    .role(Role.NGO)
                    .name(dto.getNgoName())
                    .krs(dto.getKrs())
                    .build();
            case "OFFICIAL" -> Official.builder()
                    .username(dto.getUsername())
                    .password(encodedPassword)
                    .email(dto.getEmail())
                    .role(Role.OFFICIAL)
                    .officialName(dto.getOfficialName())
                    .regon(dto.getRegon())
                    .build();
            case "ADMIN" -> Admin.builder()
                    .username(dto.getUsername())
                    .password(encodedPassword)
                    .email(dto.getEmail())
                    .role(Role.ADMIN)
                    .phone(dto.getPhone())
                    .build();
            default -> throw new IllegalArgumentException("Invalid role: " + dto.getRole());
        };

        userRepository.save(user);
    }

    public boolean loginUser(AuthRequestDto dto) {
        User user = userRepository.findByUsername(dto.getUsername());

        if (user == null) {
            throw new IllegalArgumentException("User " + dto.getUsername() + " not found");
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return true;
    }

    public Map<String, String> getAccessToken(AuthRequestDto dto) {
        User user = userRepository.findByUsername(dto.getUsername());
        String accessToken = jwtUtil.generateAccessToken(user);
        return Map.of("accessToken", accessToken);
    }

    public ResponseCookie getRefreshToken(AuthRequestDto dto) {
        User user = userRepository.findByUsername(dto.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(user);
        return cookieUtil.createHttpOnlyCookie(
                "refreshToken",
                refreshToken,
                "/api/auth",
                JwtUtil.REFRESH_TOKEN_EXPIRATION);
    }

    public Map<String, String> refreshAccessToken(String refreshToken) {
        if (refreshToken != null && jwtUtil.isTokenValid(refreshToken)) {
            String username = jwtUtil.extractUsername(refreshToken);
            String accessToken = jwtUtil.generateAccessToken(userRepository.findByUsername(username));

            Map<String, String> token = new HashMap<>();
            token.put("accessToken", accessToken);

            return token;
        }
        throw new IllegalArgumentException("Invalid refresh token");
    }
}
