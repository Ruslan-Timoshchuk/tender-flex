package pl.com.tenderflex.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.com.tenderflex.model.User;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    private final AuthenticationProvider authenticationProvider;
    
    public UserController(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }
    
    @GetMapping("/login")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void performAuthenticate(@RequestBody User user) {
        Authentication authenticatedUser = authenticationProvider
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authenticatedUser);
    }
}