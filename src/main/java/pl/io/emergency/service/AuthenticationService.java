package pl.io.emergency.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.io.emergency.dto.authorization.LoginRequestDto;
import pl.io.emergency.dto.authorization.LoginResponseDto;
import pl.io.emergency.dto.authorization.RegistrationRequestDto;
import pl.io.emergency.dto.authorization.UserDataDto;
import pl.io.emergency.entity.users.*;
import pl.io.emergency.repository.UserRepository;
import pl.io.emergency.security.CookieUtil;
import pl.io.emergency.security.JwtUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil, CookieUtil cookieUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.cookieUtil = cookieUtil;
    }

    public void registerUser(RegistrationRequestDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email is already taken");
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
                    .available(true)
                    .readyForMark(false)
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
            default -> throw new IllegalArgumentException("Invalid role: " + dto.getRole());
        };

        userRepository.save(user);
    }

    public boolean loginUser(LoginRequestDto dto) {
        User user = userRepository.findByUsername(dto.getUsername());

        if (user == null) {
            throw new IllegalArgumentException("User " + dto.getUsername() + " not found");
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return true;
    }

    public LoginResponseDto getLoginResponseDto(String accessToken, String username) {
        User user = userRepository.findByUsername(username);
        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .userData(UserDataDto.builder()
                        .username(user.getUsername())
                        .userId(user.getId())
                        .role(user.getRole().toString())
                        .build())
                .build();
    }

    public String getAccessToken(LoginRequestDto dto) {
        User user = userRepository.findByUsername(dto.getUsername());
        return jwtUtil.generateAccessToken(user);
    }

    public ResponseCookie getRefreshToken(LoginRequestDto dto) {
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
            String userId = jwtUtil.extractId(refreshToken);
            Optional<User> user = userRepository.findById(Long.valueOf(userId));
            if (user.isPresent()) {
                String accessToken = jwtUtil.generateAccessToken(user.get());

                Map<String, String> token = new HashMap<>();
                token.put("accessToken", accessToken);
                return token;
            } else {
                log.error("UserId doesnt exist.");
            }
        }
        throw new IllegalArgumentException("Invalid refresh token");
    }
}
