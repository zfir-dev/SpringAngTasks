package dev.zfir.springangtas    @Override
    protected void doFilterInternal(HttpServletRequest request,\n                                    HttpServletResponse response,\n                                    FilterChain chain) throws ServletException, IOException {\n        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);\n        \n        if (authHeader != null && authHeader.startsWith(\"Bearer \")) {\n            try {\n                String token = authHeader.substring(7);\n                String username = jwtService.validateAndGetSubject(token);\n                \n                userRepository.findByUsername(username).ifPresent(user -> {\n                    Authentication auth = new UsernamePasswordAuthenticationToken(user, null, List.of());\n                    SecurityContextHolder.getContext().setAuthentication(auth);\n                });\n            } catch (Exception e) {\n                // Invalid token, continue without authentication\n            }\n        }\n        \n        chain.doFilter(request, response);\n    }port dev.zfir.springangtasks.user.User;
import dev.zfir.springangtasks.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {
        String authHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String username = jwtService.validateAndGetSubject(authHeader.substring(7));
                userRepository.findByUsername(username).ifPresent(u -> {
                    Authentication auth = new UsernamePasswordAuthenticationToken(u, null, List.of());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                });
            } catch (Exception ignored) {}
        }
        chain.doFilter(req, res);
    }
}
