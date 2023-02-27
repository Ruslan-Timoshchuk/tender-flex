package pl.com.tenderflex.service.impl;

import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.TokenRepository;
import pl.com.tenderflex.dao.UserRepository;
import pl.com.tenderflex.dto.AuthenticationRequest;
import pl.com.tenderflex.dto.AuthenticationResponse;
import pl.com.tenderflex.model.Token;
import pl.com.tenderflex.model.TokenType;
import pl.com.tenderflex.model.User;
import pl.com.tenderflex.security.JwtService;
import pl.com.tenderflex.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.getByEmail(request.getEmail());
        String jwtToken = jwtService.generateToken(user);
        tokenRepository.revokeAllUserTokens(user.getId());
        Token token = new Token();
        token.setUser(user);
        token.setJwtToken(jwtToken);
        token.setTokenType(TokenType.BEARER);
        token.setExpired(false);
        token.setRevoked(false);
        tokenRepository.saveUserToken(token);
        return new AuthenticationResponse(jwtToken);
    }
}