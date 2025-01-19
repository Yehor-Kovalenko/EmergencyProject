package pl.io.emergency.security;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final String userId;

    public CustomAuthenticationToken(Object principal, Object credentials, String userId, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.userId = userId;
    }

}
