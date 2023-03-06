package pl.com.tenderflex.payload.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AuthenticationResponse {

    private final Integer userId;
    private final String role;

}