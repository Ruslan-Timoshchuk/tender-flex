package pl.com.tenderflex.payload.response;

import lombok.Data;

@Data
public class AuthenticationResponse {

    private final Integer userId;
    private final String role;

}