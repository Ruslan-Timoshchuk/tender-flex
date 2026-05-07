package com.flex.tender.service;

import com.flex.tender.model.embedded.AuthenticatedPrincipal;
import com.flex.tender.payload.request.AuthenticationRequest;
import com.flex.tender.payload.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticatedPrincipal authenticate(AuthenticationRequest authenticationRequest);
    
    AuthenticationResponse resolveAuthenticationResponse(AuthenticatedPrincipal authenticatedPrincipal);

    AuthenticationResponse loadAuthenticationState(Integer principalid);

}