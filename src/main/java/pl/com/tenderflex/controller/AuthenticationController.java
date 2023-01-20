package pl.com.tenderflex.controller;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.com.tenderflex.dto.AuthenticationRequest;
import pl.com.tenderflex.exception.AuthenticationControllerException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationProvider authenticationProvider;
    
    public AuthenticationController(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }
    
    @GetMapping("/login")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void performAuthenticate(@RequestBody @Valid AuthenticationRequest reguest,  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new AuthenticationControllerException("Email and password should not be empty");
        }
        Authentication authenticatedUser = authenticationProvider
                .authenticate(new UsernamePasswordAuthenticationToken(reguest.getEmail(), reguest.getPassword()));
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authenticatedUser);
    }
}