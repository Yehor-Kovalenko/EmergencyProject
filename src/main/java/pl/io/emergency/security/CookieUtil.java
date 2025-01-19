package pl.io.emergency.security;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class CookieUtil {
    public ResponseCookie createHttpOnlyCookie(String name, String value, String path, long maxAge) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(true)
                .path(path)
                .sameSite("Strict")
                .maxAge(maxAge)
                .build();
    }
}
