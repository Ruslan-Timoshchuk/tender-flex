package com.flex.tender.model.embedded;

import java.util.List;

/**
 * @author Ruslan Timoshchuk
 */
public record AuthenticatedPrincipal(
        Integer id,
        String email, 
        List<PrincipalAuthority> authorities) {
}