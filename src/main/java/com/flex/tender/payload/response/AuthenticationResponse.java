package com.flex.tender.payload.response;

import java.util.List;

public record AuthenticationResponse(
        Integer userId, 
        List<String> authorities) {
}