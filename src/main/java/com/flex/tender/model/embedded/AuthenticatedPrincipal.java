package com.flex.tender.model.embedded;

import java.util.List;

import com.flex.tender.model.User;

/**
 * @author Ruslan Timoshchuk
 */
public record AuthenticatedPrincipal(
        Integer id,
        String email, 
        List<PrincipalAuthority> authorities) {
    
    public AuthenticatedPrincipal(User user) {
        this(user.getId(), 
             user.getEmail(), 
             user.getAuthorities()
                 .stream()
                 .map(PrincipalAuthority::new)
                 .toList());
    }
    
}