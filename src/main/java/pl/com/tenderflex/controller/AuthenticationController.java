package pl.com.tenderflex.controller;

import static org.springframework.http.HttpStatus.*;
import javax.validation.Valid;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dto.AuthenticationRequest;
import pl.com.tenderflex.dto.AuthenticationResponse;
import pl.com.tenderflex.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @ResponseStatus(OK)
    public AuthenticationResponse performAuthenticate(@RequestBody @Valid AuthenticationRequest request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadCredentialsException("Email and password should not be empty");
        }
        return authenticationService.authenticate(request);
    }
}