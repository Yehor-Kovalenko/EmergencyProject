package pl.io.emergency.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Date;

@Data
public class RegistrationUserDto {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Role is required")
    private String role;

    private String firstName;
    private String lastName;
    private Date birthDate;
    private Long organizationId;

    private String ngoName;
    private String krs;

    private String officialName;
    private String regon;
}
