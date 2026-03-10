package com.flex.tender.model;

import java.util.List;

public record AuthenticatedPrincipal(
        Integer id,
        String email, 
        List<String> authorities) {
    
    public AuthenticatedPrincipal(User user) {
        this(user.getId(), 
             user.getEmail(), 
             user.getAuthorities()
                 .stream()
                 .map(authority -> authority.getTitle().name())
                 .toList());
    }
    
}