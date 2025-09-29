package dev.zfir.springangtasks.auth;

import dev.zfir.springangtasks.security.JwtService;
import dev.zfir.springangtasks.user.User;
import dev.zfir.springangtasks.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class AuthController {

    private final UserRepository users;
    private final BCryptPasswordEncoder encoder;
    private final JwtService jwt;

    public AuthController(UserRepository users, BCryptPasswordEncoder encoder, JwtService jwt) {
        this.users = users;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    public TokenResponse register(@Valid @RequestBody RegisterRequest req) {
        if (users.existsByUsername(req.username())) {
            throw new RuntimeException("Username taken");
        }
        
        User user = users.save(User.builder()
                .username(req.username())
                .passwordHash(encoder.encode(req.password()))
                .build());
        
        return new TokenResponse(jwt.generate(user.getUsername()));
    }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest req) {
        User user = users.findByUsername(req.username())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        
        if (!encoder.matches(req.password(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }
        
        return new TokenResponse(jwt.generate(user.getUsername()));
    }
}
