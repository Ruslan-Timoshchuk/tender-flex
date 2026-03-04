package com.flex.tender.model;

import java.util.List;

public record AuthenticatedPrincipal(
        Integer id,
        String email, 
        List<String> authorities) {
}