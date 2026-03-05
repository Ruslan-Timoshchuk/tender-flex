package com.flex.tender.service;

import org.springframework.http.HttpHeaders;
import com.flex.tender.model.JwtAuthenticationToken;
import jakarta.servlet.http.Cookie;

public interface JwtCookiesService {

    HttpHeaders issueJwtCookie(JwtAuthenticationToken authenticationToken);

    String extractJwtToken(Cookie[] cookies, String cookieName);
 
}