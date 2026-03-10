package com.flex.tender.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.flex.tender.model.AuthenticatedPrincipal;
import com.flex.tender.model.User;
import com.flex.tender.payload.request.AuthenticationRequest;
import com.flex.tender.payload.response.AuthenticationResponse;
import com.flex.tender.service.AuthenticationService;
import com.flex.tender.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    public static final String LOG_MSG_ON_BAD_CREDENTIALS = "Authentication failed for email = {}: the password is invalid ";

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticatedPrincipal authenticate(AuthenticationRequest credential) {
        final String email = credential.getEmail();
        User principal = userService.findByEmail(email);
        if (isAuthenticated(credential, principal)) {
            return new AuthenticatedPrincipal(principal);
        } else {
            log.warn(LOG_MSG_ON_BAD_CREDENTIALS, principal.getEmail());
            throw new BadCredentialsException("Provided password is incorrect");
        }
    }

    @Override
    public AuthenticationResponse resolveAuthenticationResponse(AuthenticatedPrincipal authenticatedPrincipal) {
        return new AuthenticationResponse(authenticatedPrincipal.email(), authenticatedPrincipal.authorities());
    }

    private boolean isAuthenticated(AuthenticationRequest credential, User principal) {
        return passwordEncoder.matches(credential.getPassword(), principal.getPassword());
    }
    
}