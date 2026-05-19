package com.flex.tender.service;

import java.util.UUID;
import com.flex.tender.model.embedded.PrincipalDetails;
import com.flex.tender.payload.request.AuthenticationRequest;
import com.flex.tender.payload.response.AuthenticationResponse;

public interface AuthenticationService {

    PrincipalDetails authenticate(AuthenticationRequest authenticationRequest);
    
    AuthenticationResponse resolveAuthenticationResponse(PrincipalDetails authenticatedPrincipal, UUID principalUuid);

    AuthenticationResponse loadAuthenticationState(UUID principalUuid);

}