package pl.io.emergency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.io.emergency.entity.PasswordResetToken;
import pl.io.emergency.entity.users.User;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByUser(User user);
}