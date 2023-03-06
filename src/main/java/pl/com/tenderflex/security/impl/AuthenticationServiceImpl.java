package pl.com.tenderflex.security.impl;

import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.payload.AuthenticationDetails;
import pl.com.tenderflex.payload.request.AuthenticationRequest;
import pl.com.tenderflex.security.AuthenticationService;
import pl.com.tenderflex.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    public AuthenticationDetails authenticate(AuthenticationRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetailsImpl user = (UserDetailsImpl) userDetailsService.loadUserByUsername(request.getEmail());
        String userRole = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElseThrow();
        return new AuthenticationDetails(user.getId(), userRole, jwtService.generateJwtCookie(user.getUsername()));
    }
}