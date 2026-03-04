package com.flex.tender.payload.response;

import java.util.List;

public record AuthenticationResponse(
        String email, 
        List<String> authorities) {
}