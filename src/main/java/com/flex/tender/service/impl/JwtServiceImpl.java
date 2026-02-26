package com.flex.tender.service.impl;

import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import com.flex.tender.model.User;
import com.flex.tender.model.enumeration.EAuthority;
import com.flex.tender.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

    public static final String SLASH_LINE = "/";
    private static final String AUTHORITIES_CLAIM = "authorities";
   
    @Value("${jwt.expiration.token}")
    private Integer jwtExpirationTokenMs;
    @Value("${jwt.access.signing.key}")
    private String jwtAccessSigningKey;

    @Override
    public String generateToken(User user) {
     return Jwts
        .builder()
        .subject(String.valueOf(user.getId()))
        .claim(AUTHORITIES_CLAIM, 
               user.getAuthorityTitles()
                   .stream()
                   .map(EAuthority::name)
                   .toList())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + jwtExpirationTokenMs))
        .signWith(get())
        .compact();
    }

    @Override
    public boolean isValid(String token) {
        try {
            Jwts.parser()
                .verifyWith(get())
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(get())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    @Override
    public Integer extractUserId(Claims claims) {
        return Integer.valueOf(claims.getSubject());
    }

    @Override
    public List<SimpleGrantedAuthority> extractAuthorities(Claims claims) {
        if (claims.get(AUTHORITIES_CLAIM) instanceof List<?> list) {
            return list.stream()
                     .map(String::valueOf)
                     .map(SimpleGrantedAuthority::new).toList();
        } else {
            throw new JwtException("Invalid authorities claim: expected array");
        }
    }

    private SecretKey get() {
        byte[] keyBytes = Decoders
                            .BASE64
                            .decode(jwtAccessSigningKey);
        return Keys
                 .hmacShaKeyFor(keyBytes);
    }
    
}