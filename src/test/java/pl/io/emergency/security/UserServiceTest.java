package pl.io.emergency.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.io.emergency.dto.RegistrationUserDto;
import pl.io.emergency.entity.User;
import pl.io.emergency.repository.UserRepository;
import pl.io.emergency.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        RegistrationUserDto dto = new RegistrationUserDto();
        dto.setUsername("testUser");
        dto.setPassword("password");
        dto.setEmail("test@example.com");
        dto.setRole("GIVER");
        dto.setPhone("123456789");

        when(userRepository.existsByUsername(dto.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");

        userService.registerUser(dto);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUsernameOrEmailExists() {
        RegistrationUserDto dto = new RegistrationUserDto();
        dto.setUsername("testUser");
        dto.setEmail("test@example.com");

        when(userRepository.existsByUsername(dto.getUsername())).thenReturn(true);
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(dto);
        });

        assertEquals("Username is already taken", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionForInvalidRole() {
        RegistrationUserDto dto = new RegistrationUserDto();
        dto.setUsername("testUser");
        dto.setPassword("password");
        dto.setEmail("test@example.com");
        dto.setRole("INVALID_ROLE");

        when(userRepository.existsByUsername(dto.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(dto);
        });

        assertTrue(exception.getMessage().contains("Invalid role"));
        verify(userRepository, never()).save(any());
    }
}
