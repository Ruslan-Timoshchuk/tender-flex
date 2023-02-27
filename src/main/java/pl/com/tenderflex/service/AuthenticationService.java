package pl.com.tenderflex.service;

import pl.com.tenderflex.dto.AuthenticationRequest;
import pl.com.tenderflex.dto.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request);
    
}