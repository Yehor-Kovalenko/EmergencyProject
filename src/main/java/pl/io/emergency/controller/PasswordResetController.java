package pl.io.emergency.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.io.emergency.dto.PasswordResetRequestDto;
import pl.io.emergency.dto.PasswordResetConfirmDto;
import pl.io.emergency.service.PasswordResetService;

@RestController
@RequestMapping("/api/auth/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/request")
    public ResponseEntity<Void> requestPasswordReset(@RequestBody PasswordResetRequestDto request) {
        passwordResetService.createPasswordResetTokenForEmail(request.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/confirm")
    public ResponseEntity<Void> confirmReset(@RequestBody PasswordResetConfirmDto confirmDto) {
        passwordResetService.confirmPasswordReset(confirmDto.getToken(), confirmDto.getNewPassword());
        return ResponseEntity.ok().build();
    }
}