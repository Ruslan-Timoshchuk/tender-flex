package com.flex.tender.service.impl;

import static java.util.stream.Collectors.toSet;
import static com.flex.tender.model.constants.JwtClaims.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import com.flex.tender.model.embedded.PrincipalSummary;
import com.flex.tender.service.JwtClaimsExtractor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtClaimsExtractorImpl implements JwtClaimsExtractor {
    
    private final SecretKey accessTokenSecretKey;
    
    @Override
    public Claims extractAccessTokenClaims(String token) {
        return Jwts
                 .parser()
                 .verifyWith(accessTokenSecretKey)
                 .build()
                 .parseSignedClaims(token)
                 .getPayload();
    }
    
    @Override
    public PrincipalSummary extractPrincipal(Claims claims) {
        return new PrincipalSummary(
                claims.get(USER_ID, Integer.class), 
                UUID.fromString(claims.getSubject()));
    }

    @Override
    public Set<SimpleGrantedAuthority> extractAuthorities(Claims claims) {
        if (claims.get(AUTHORITIES) instanceof List<?> list) {
            return list.stream()
                       .map(String::valueOf)
                       .map(SimpleGrantedAuthority::new)
                       .collect(toSet());
        } else {
            throw new JwtException("Invalid authorities claim: expected array");
        }
    }
    
}