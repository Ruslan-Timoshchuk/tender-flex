package pl.com.tenderflex.security.impl;

import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.payload.request.AuthenticationRequest;
import pl.com.tenderflex.security.AuthenticationService;
import pl.com.tenderflex.security.JwtService;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    
    @Override
    public ResponseCookie authenticate(AuthenticationRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
        return jwtService.generateJwtCookie(user.getUsername());
    }
}