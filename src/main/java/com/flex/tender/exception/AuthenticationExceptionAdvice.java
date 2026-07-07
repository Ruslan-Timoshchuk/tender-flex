package com.flex.tender.exception;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import io.jsonwebtoken.JwtException;

@RestControllerAdvice
public class AuthenticationExceptionAdvice {

    public static final String EX_MSG_ON_BAD_CREDENTIALS = "Invalid email or password";

    @ExceptionHandler({ UsernameNotFoundException.class, BadCredentialsException.class })
    @ResponseStatus(UNAUTHORIZED)
    public ExceptionHandlerResponse handleAuthenticationException(AuthenticationException exception) {
        return new ExceptionHandlerResponse(now(), UNAUTHORIZED.value(), UNAUTHORIZED, EX_MSG_ON_BAD_CREDENTIALS);
    }
    
    @ExceptionHandler(JwtException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ExceptionHandlerResponse handleJwtException(JwtException exception) {
        return new ExceptionHandlerResponse(now(), UNAUTHORIZED.value(), UNAUTHORIZED, exception.getMessage());
    }

}