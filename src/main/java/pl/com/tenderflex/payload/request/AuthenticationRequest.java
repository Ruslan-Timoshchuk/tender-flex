package pl.com.tenderflex.payload.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthenticationRequest {

    @NotEmpty(message = "Email cannot be empty")
    private final String email;
    @NotEmpty(message = "Password cannot be empty")
    private final String password;

}