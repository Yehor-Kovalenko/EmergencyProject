package pl.io.emergency.dto;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String token;
    private String message;

    public AuthResponseDto(String token, String message) {
        this.token = token;
        this.message = message;
    }
}
