package com.flex.tender.service.write;

import com.flex.tender.model.embedded.PrincipalDetails;
import com.flex.tender.model.embedded.JwtAuthenticationToken;

public interface JwtTokenGenerator {

    JwtAuthenticationToken issueAuthenticationToken(PrincipalDetails authenticatedPrincipal);

}