package com.flex.tender.controller.filter;

import static java.util.Objects.isNull;
import java.io.IOException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import com.flex.tender.model.constants.CookieNames;
import com.flex.tender.model.embedded.PrincipalSummary;
import com.flex.tender.service.JwtClaimsExtractor;
import com.flex.tender.service.JwtCookiesService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final RequestMatcher publicEndpointsMatcher;
    private final JwtCookiesService jwtCookiesService;
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtClaimsExtractor jwtClaimsExtractor;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            final SecurityContext securityContext = SecurityContextHolder.getContext();
            if (isNull(securityContext.getAuthentication())) {
                final String jwtAccessToken = jwtCookiesService.extractJwtToken(request.getCookies(), CookieNames.ACCESS_TOKEN);
                final WebAuthenticationDetails webAuthenticationDetails = new WebAuthenticationDetailsSource()
                        .buildDetails(request);
                final Authentication authentication = buildAuthenticationToken(jwtAccessToken,
                        webAuthenticationDetails);
                securityContext.setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return publicEndpointsMatcher.matches(request);
    }
    
    private Authentication buildAuthenticationToken(String accessToken, WebAuthenticationDetails details) {
        final Claims claims = jwtClaimsExtractor.extractAccessTokenClaims(accessToken);
        final PrincipalSummary principal = jwtClaimsExtractor.extractPrincipal(claims);
        final Set<SimpleGrantedAuthority> authorities = jwtClaimsExtractor.extractAuthorities(claims);
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal,
                null, authorities);
        authenticationToken.setDetails(details);
        return authenticationToken;
    }
    
}