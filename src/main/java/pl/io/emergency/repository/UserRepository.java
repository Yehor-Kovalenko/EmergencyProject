package pl.io.emergency.repository;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.io.emergency.entity.users.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(@NotBlank(message = "Username is required") String username);
    boolean existsByEmail(@NotBlank(message = "Email is required") String email);
    User findByUsername(@NotBlank(message = "Username is required") String username);
    Optional<User> findByEmail(String email);
}
