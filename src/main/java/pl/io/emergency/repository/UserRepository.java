package pl.io.emergency.repository;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.io.emergency.entity.users.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(@NotBlank(message = "Username is required") String username);
    boolean existsByEmail(@NotBlank(message = "Email is required") String email);
    @Query("SELECT u.email FROM User u WHERE u.id = :id")
    String findEmailById(@Param("id") Long id);
    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT(:query, '%'))")
    List<User> searchUsersByName(String query);
    User findByUsername(@NotBlank(message = "Username is required") String username);
}
