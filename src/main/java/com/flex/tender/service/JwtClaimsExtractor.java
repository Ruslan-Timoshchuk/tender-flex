package com.flex.tender.service;

import java.util.Set;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.flex.tender.model.embedded.PrincipalSummary;
import io.jsonwebtoken.Claims;

public interface JwtClaimsExtractor {

    Claims extractAccessTokenClaims(String token);

    PrincipalSummary extractPrincipal(Claims claims);

    Set<SimpleGrantedAuthority> extractAuthorities(Claims claims);

}