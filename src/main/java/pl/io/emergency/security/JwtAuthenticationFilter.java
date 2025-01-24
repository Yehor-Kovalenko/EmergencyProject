package pl.io.emergency.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import pl.io.emergency.entity.users.User;
import pl.io.emergency.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        if (token != null && jwtUtil.isTokenValid(token)) {
            Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();

            if (existingAuth != null) {
                log.debug("Found existing authentication: {}", existingAuth);
            }

            if (existingAuth == null || existingAuth instanceof AnonymousAuthenticationToken) {
                String userId = jwtUtil.extractId(token);
                if (!userId.matches("\\d+")) {
                    log.error("userId is not a number");
                }

                Optional<User> opt_user = userRepository.findById(Long.parseLong(userId));

                if (opt_user.isPresent()) {
                    String username = opt_user.get().getUsername();
                    String role = opt_user.get().getRole().name();
                    log.info("Entering filter. Username: {} | userId: {}", username, userId);

                    CustomAuthenticationToken authenticationToken = new CustomAuthenticationToken(
                            username,
                            null,
                            userId,
                            List.of(new SimpleGrantedAuthority("ROLE_" + role)));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    log.info("SecurityContext: {}", SecurityContextHolder.getContext().getAuthentication());
                } else {
                    log.error("UserId: {} does not exist in db", userId);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
