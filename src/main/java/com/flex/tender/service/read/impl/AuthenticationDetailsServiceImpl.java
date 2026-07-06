package com.flex.tender.service.read.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.flex.tender.model.User;
import com.flex.tender.model.embedded.PrincipalDetails;
import com.flex.tender.payload.mapper.AuthenticationDetailsMapper;
import com.flex.tender.payload.request.AuthenticationRequest;
import com.flex.tender.payload.response.AuthenticationResponse;
import com.flex.tender.service.read.AuthenticationDetailsService;
import com.flex.tender.service.read.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationDetailsServiceImpl implements AuthenticationDetailsService {

    public static final String LOG_MSG_ON_BAD_CREDENTIALS = "Authentication failed for email = {}: the password is invalid ";

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationDetailsMapper authenticationDetailsMapper;

    private final Map<UUID, PrincipalDetails> authenticationStateCaching = new ConcurrentHashMap<>();
    
    @Override
    public PrincipalDetails authenticate(AuthenticationRequest credential) {
        final String email = credential.getEmail();
        User principal = userService.findByEmail(email);
        if (isAuthenticated(credential, principal)) {
            return authenticationDetailsMapper.toPrincipal(principal);
        } else {
            log.warn(LOG_MSG_ON_BAD_CREDENTIALS, principal.getEmail());
            throw new BadCredentialsException("Provided password is incorrect");
        }
    }

    @Override
    public AuthenticationResponse resolveAuthenticationResponse(PrincipalDetails authenticatedPrincipal,
            UUID principalUuid) {
        authenticationStateCaching.put(principalUuid, authenticatedPrincipal);
        return authenticationDetailsMapper.toResponse(authenticatedPrincipal);
    }
    
    @Override
    public AuthenticationResponse loadAuthenticationState(UUID principalUuid) {
        return authenticationDetailsMapper.toResponse(authenticationStateCaching.get(principalUuid));
    }
    
    private boolean isAuthenticated(AuthenticationRequest credential, User principal) {
        return passwordEncoder.matches(credential.getPassword(), principal.getPassword());
    }
    
}