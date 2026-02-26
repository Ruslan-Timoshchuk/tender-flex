package com.flex.tender.service;

import org.springframework.http.ResponseCookie;
import jakarta.servlet.http.Cookie;

public interface JwtCookiesService {

    String extractJwt(Cookie[] cookies);

    ResponseCookie generateJwtCookie(String jwtToken);

    String extractJwtToken(Cookie[] cookies, String cookieName);

}