package com.flex.tender.model.embedded;

import java.util.List;

/**
 * @author Ruslan Timoshchuk
 */
public record PrincipalDetails(
        Integer id,
        String email, 
        List<PrincipalAuthority> authorities) {
}