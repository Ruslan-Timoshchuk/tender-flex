package com.flex.tender.service.impl;

import static com.flex.tender.model.constants.JwtClaims.*;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.flex.tender.model.embedded.AuthenticatedPrincipal;
import com.flex.tender.model.embedded.JwtAuthenticationToken;
import com.flex.tender.service.JwtTokenGenerator;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtTokenGeneratorImpl implements JwtTokenGenerator {

    @Value("${jwt.token.issuer}")
    private String jwtTokenIssuer;
    @Value("${jwt.access.token.live}")
    private Duration jwtAccessTokenLive;
    private final SecretKey accessTokenSecretKey;

    @Override
    public JwtAuthenticationToken issueAuthenticationToken(AuthenticatedPrincipal authenticatedPrincipal) {
        return new JwtAuthenticationToken(generateAccessToken(authenticatedPrincipal.id(),
                authenticatedPrincipal
                    .authorities()
                    .stream()
                    .map(authority -> authority.authority().name())
                    .toList()));
    }

    private String generateAccessToken(Integer id, List<String> authorities) {
        return Jwts
                 .builder()
                 .subject(String.valueOf(id))
                 .claim(ISSUER, jwtTokenIssuer)
                 .claim(AUTHORITIES, authorities)
                 .issuedAt(new Date(System.currentTimeMillis()))
                 .expiration(new Date(System.currentTimeMillis() + jwtAccessTokenLive.toMillis()))
                 .signWith(accessTokenSecretKey)
                 .compact();
    }

}