package com.flex.tender.service.impl;

import static java.lang.String.format;
import static java.net.URLDecoder.decode;
import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.util.StringUtils.startsWithIgnoreCase;
import static java.util.Objects.nonNull;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.util.StringUtils;
import com.flex.tender.exception.CookiesNotPresentException;
import com.flex.tender.service.JwtCookiesService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;

public class JwtCookiesServiceImpl implements JwtCookiesService {

    public static final String LIMIT_THE_SCOPE = "None";
    public static final String ABSOLUTE_API_PATH = "/";
    public static final String SLASH_LINE = "/";
    public static final String BEARER_PREFIX = "Bearer";
    public static final String JWT_COOKIE_NOT_FOUND = "JWT token cookie with [name %s] was not found";
    public static final String COOKIES_NOT_PRESENT = "Cookies are not present";
    
    @Value("${jwt.cookie.name}")
    private String jwtCookieName;
    @Value("${jwt.cookie.max.age}")
    private Integer jwtCookieMaxAgeSec;
    
    @Override
    public ResponseCookie generateJwtCookie(String jwtToken) {
        return ResponseCookie
                .from(jwtCookieName, encode(jwtToken, UTF_8))
                .httpOnly(true)
                .secure(true)
                .sameSite(LIMIT_THE_SCOPE)
                .path(ABSOLUTE_API_PATH)
                .maxAge(jwtCookieMaxAgeSec)
                .build();
    }
    
    @Override
    public String extractJwt(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(jwtCookieName) && StringUtils.hasText(cookie.getValue()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new CookiesNotPresentException("JWT cookie is missing or empty"));
    }

    @Override
    public String extractJwtToken(Cookie[] cookies, String cookieName) {
        if (nonNull(cookies)) {
            for (Cookie cookie : cookies) {
                String cookieValue = cookie.getValue();
                if (cookie.getName().equals(cookieName) && StringUtils.hasText(cookieValue)
                        && startsWithIgnoreCase(cookieValue, BEARER_PREFIX)) {
                    return decode(cookieValue.substring(BEARER_PREFIX.length() + 1), UTF_8);
                }
            }
            throw new JwtException(format(JWT_COOKIE_NOT_FOUND, cookieName));
        } else {
            throw new JwtException(COOKIES_NOT_PRESENT);
        }
    }
    
}