package com.flex.tender.model.embedded;

import java.util.UUID;

/**
 * @author Ruslan Timoshchuk
 */
public record JwtAuthenticationToken(
        UUID principalUuid,
        String accessToken) {   
}