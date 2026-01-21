package com.flex.tender.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.flex.tender.model.User;
import com.flex.tender.model.enumeration.EAuthority;
import com.flex.tender.payload.AuthenticationDetails;
import com.flex.tender.payload.request.AuthenticationRequest;
import com.flex.tender.service.AuthenticationService;
import com.flex.tender.service.JwtService;
import com.flex.tender.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    public static final String LOG_MSG_ON_BAD_CREDENTIALS = "Authentication failed for email = {}: the password is invalid ";
    
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationDetails authenticate(AuthenticationRequest authenticationRequest) {
        User user = userService.findByEmail(authenticationRequest.getEmail());
        if (passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
            List<String> authorities = user.getAuthorityTitles().stream().map(EAuthority::name).toList();
            return new AuthenticationDetails(user.getId(), authorities, jwtService.generateJwtCookie(user));
        } else {
            log.warn(LOG_MSG_ON_BAD_CREDENTIALS, authenticationRequest.getEmail());
            throw new BadCredentialsException("Provided password is incorrect");      
    }
    }

}