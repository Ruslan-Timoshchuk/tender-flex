package pl.com.tenderflex.payload.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthenticationResponse {

    private final Integer userId;
    private final String role;

}