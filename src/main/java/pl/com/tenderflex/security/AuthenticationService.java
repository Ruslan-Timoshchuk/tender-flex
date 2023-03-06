package pl.com.tenderflex.security;

import org.springframework.http.ResponseCookie;
import pl.com.tenderflex.payload.request.AuthenticationRequest;

public interface AuthenticationService {

    ResponseCookie authenticate(AuthenticationRequest request);
    
}