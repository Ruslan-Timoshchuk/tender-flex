package com.flex.tender.service.impl;

import static java.lang.String.format;
import static java.net.URLDecoder.decode;
import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.util.StringUtils.startsWithIgnoreCase;
import static java.util.Objects.nonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.flex.tender.model.JwtAuthenticationToken;
import com.flex.tender.model.constants.CookieNames;
import com.flex.tender.service.JwtCookiesService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;

@Service
public class JwtCookiesServiceImpl implements JwtCookiesService {

    public static final String JWT_FORMAT = "%s %s";
    public static final String LIMIT_THE_SCOPE = "None";
    public static final String ABSOLUTE_API_PATH = "/";
    public static final String BEARER_PREFIX = "Bearer";
    public static final String JWT_COOKIE_NOT_FOUND = "JWT token cookie with [name %s] was not found";
    public static final String COOKIES_NOT_PRESENT = "Cookies are not present";
    
    @Value("${jwt.cookie.max.age}")
    private Integer jwtCookieMaxAgeSec;
    
    @Override
    public HttpHeaders issueJwtCookie(JwtAuthenticationToken authenticationToken) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(SET_COOKIE,
                buildCookie(CookieNames.ACCESS_TOKEN,
                        format(JWT_FORMAT, BEARER_PREFIX, authenticationToken.accessToken()),
                        ABSOLUTE_API_PATH, jwtCookieMaxAgeSec));
        return headers;
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
    
    private String buildCookie(String name, String value, String path, Integer lifeTime) {
        return ResponseCookie
                 .from(name, encode(value, UTF_8))
                 .httpOnly(true)
                 .secure(true)
                 .sameSite(LIMIT_THE_SCOPE)
                 .path(path)
                 .maxAge(lifeTime)
                 .build()
                 .toString();
    }
    
}