package pl.io.emergency.dto.authorization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDto {
    private String accessToken;
    private UserDataDto userData;

    public LoginResponseDto(String accessToken) {
        this.accessToken = accessToken;
        this.userData = new UserDataDto();
    }

}
