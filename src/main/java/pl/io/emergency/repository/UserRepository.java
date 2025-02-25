package pl.io.emergency.repository;

import jakarta.validation.constraints.NotBlank;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.io.emergency.entity.users.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(@NotBlank(message = "Username is required") String username);
    boolean existsByEmail(@NotBlank(message = "Email is required") String email);

    @Query("SELECT u.email FROM User u WHERE u.id = :id")
    String findEmailById(@Param("id") Long id);
    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT(:query, '%'))")
    List<User> searchUsersByName(String query);
    @Query("SELECT u.username FROM User u WHERE u.id = :id")
    String findUsernameById(@Param("id") Long id);

    User findByUsername(@NotBlank(message = "Username is required") String username);

    Optional<User> findById(@NotBlank Long id);
}
