package com.flex.tender.service;

import java.util.List;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.flex.tender.model.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

public interface JwtService {

    ResponseCookie generateJwtCookie(User user);
    
    String generateToken(User user);
    
    String getJwtFromCookies(HttpServletRequest request);
    
    boolean isValid(String authToken);

    Claims extractClaims(String token);

    Integer extractUserId(Claims claims);
    
    List<SimpleGrantedAuthority> extractAuthorities(Claims claims);
    
}