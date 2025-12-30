package com.flex.tender.service.impl;

import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.payload.AuthenticationDetails;
import pl.com.tenderflex.payload.request.AuthenticationRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import com.flex.tender.model.User;
import com.flex.tender.service.AuthenticationService;
import com.flex.tender.service.JwtService;
import com.flex.tender.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public AuthenticationDetails authenticate(AuthenticationRequest request) {
        User user = userService.findByEmail(request.getEmail());
        String userRole = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElseThrow();
        return new AuthenticationDetails(user.getId(), userRole, jwtService.generateJwtCookie(user));
    }

}