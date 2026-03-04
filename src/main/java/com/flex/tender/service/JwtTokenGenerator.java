package com.flex.tender.service;

import com.flex.tender.model.AuthenticatedPrincipal;
import com.flex.tender.model.JwtAuthenticationToken;

public interface JwtTokenGenerator {

    JwtAuthenticationToken issueAuthenticationToken(AuthenticatedPrincipal authenticatedPrincipal);

}