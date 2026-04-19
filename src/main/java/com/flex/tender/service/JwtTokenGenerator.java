package com.flex.tender.service;

import com.flex.tender.model.embedded.AuthenticatedPrincipal;
import com.flex.tender.model.embedded.JwtAuthenticationToken;

public interface JwtTokenGenerator {

    JwtAuthenticationToken issueAuthenticationToken(AuthenticatedPrincipal authenticatedPrincipal);

}