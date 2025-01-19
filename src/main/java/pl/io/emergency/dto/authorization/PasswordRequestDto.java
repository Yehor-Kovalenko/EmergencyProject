package pl.io.emergency.dto.authorization;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordRequestDto {
    @NotBlank(message = "Username is required")
    private String username;
}
