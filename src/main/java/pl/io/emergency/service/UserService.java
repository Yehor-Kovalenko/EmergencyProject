package pl.io.emergency.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.io.emergency.dto.RegistrationUserDto;
import pl.io.emergency.entity.*;
import pl.io.emergency.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
            default -> throw new IllegalArgumentException("Invalid role: " + dto.getRole());
        };

        userRepository.save(user);
    }
}
