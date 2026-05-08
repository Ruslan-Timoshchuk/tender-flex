package com.flex.tender.service;

import java.util.Set;
import java.util.UUID;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import io.jsonwebtoken.Claims;

public interface JwtClaimsExtractor {

    Claims extractAccessTokenClaims(String token);

    UUID extractPrincipalUuid(Claims claims);

    Set<SimpleGrantedAuthority> extractAuthorities(Claims claims);

}