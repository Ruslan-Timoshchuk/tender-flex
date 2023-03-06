package pl.com.tenderflex.security;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

public interface JwtService {

    ResponseCookie generateJwtCookie(String userEmail);
    
    String generateTokenFromUserEmail(String userEmail);
    
    String getJwtFromCookies(HttpServletRequest request);
    
    boolean isJwtTokenValid(String authToken);
    
    String getUserEmailFromJwtToken(String token);
    
}