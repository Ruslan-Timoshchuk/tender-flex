package com.flex.tender.service;

import java.util.Set;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import io.jsonwebtoken.Claims;

public interface JwtClaimsExtractor {

    Claims extractAccessTokenClaims(String token);

    Integer extractUserId(Claims claims);

    Set<SimpleGrantedAuthority> extractAuthorityNames(Claims claims);

}