package pl.com.tenderflex.security;

import pl.com.tenderflex.payload.AuthenticationDetails;
import pl.com.tenderflex.payload.request.AuthenticationRequest;

public interface AuthenticationService {

    AuthenticationDetails authenticate(AuthenticationRequest request);

}