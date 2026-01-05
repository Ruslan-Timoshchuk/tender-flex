package com.flex.tender.service.impl;

import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import com.flex.tender.model.User;
import com.flex.tender.payload.AuthenticationDetails;
import com.flex.tender.payload.request.AuthenticationRequest;
import com.flex.tender.service.AuthenticationService;
import com.flex.tender.service.JwtService;
import com.flex.tender.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public AuthenticationDetails authenticate(AuthenticationRequest authenticationRequest) {
        User user = userService.findByEmail(authenticationRequest.getEmail());
        List<String> authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return new AuthenticationDetails(user.getId(), authorities, jwtService.generateJwtCookie(user));
    }

}