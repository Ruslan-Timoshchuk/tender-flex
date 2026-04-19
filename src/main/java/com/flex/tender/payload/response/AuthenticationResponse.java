package com.flex.tender.payload.response;

import java.util.List;

/**
 * @author Ruslan Timoshchuk
 */
public record AuthenticationResponse(
        Integer userId,
        String email, 
        List<AuthorityResponse> authorities) { 
}