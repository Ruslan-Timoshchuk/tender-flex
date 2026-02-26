package com.flex.tender.service;

import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.flex.tender.model.User;
import io.jsonwebtoken.Claims;

public interface JwtService {
    
    String generateToken(User user);
    
    boolean isValid(String authToken);

    Claims extractClaims(String token);

    Integer extractUserId(Claims claims);
    
    List<SimpleGrantedAuthority> extractAuthorities(Claims claims);
    
}