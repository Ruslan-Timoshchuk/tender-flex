package com.flex.tender.service.impl;

import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;
import com.flex.tender.model.User;
import com.flex.tender.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import pl.com.tenderflex.exception.CookiesNotPresentException;

@Service
public class JwtServiceImpl implements JwtService {

    public static final String SLASH_LINE = "/";
    private static final String AUTHORITIES_CLAIM = "authorities";
   
    @Value("${jwt.cookie.name}")
    private String jwtCookieName;
    @Value("${jwt.cookie.max.age}")
    private Integer jwtCookieMaxAgeSec;
    @Value("${jwt.expiration.token}")
    private Integer jwtExpirationTokenMs;
    @Value("${jwt.access.signing.key}")
    private String jwtAccessSigningKey;

    @Override
    public ResponseCookie generateJwtCookie(User user) {
        String jwt = generateToken(user);
        return ResponseCookie.from(jwtCookieName, jwt).path(SLASH_LINE).maxAge(jwtCookieMaxAgeSec).httpOnly(true).build();
    }

    @Override
    public String generateToken(User user) {
     return Jwts
        .builder()
        .subject(user.getEmail())
        .claim(AUTHORITIES_CLAIM, user.getAuthorities())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + jwtExpirationTokenMs))
        .signWith(get())
        .compact();
    }

    @Override
    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookieName);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            throw new CookiesNotPresentException("There are invalid cookies in request");
        }
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
    public String extractEmail(Claims claims) {
        return claims.getSubject();
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