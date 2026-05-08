package com.flex.tender.service;

import java.util.UUID;
import com.flex.tender.model.embedded.AuthenticatedPrincipal;
import com.flex.tender.payload.request.AuthenticationRequest;
import com.flex.tender.payload.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticatedPrincipal authenticate(AuthenticationRequest authenticationRequest);
    
    AuthenticationResponse resolveAuthenticationResponse(AuthenticatedPrincipal authenticatedPrincipal, UUID principalUuid);

    AuthenticationResponse loadAuthenticationState(UUID principalUuid);

}