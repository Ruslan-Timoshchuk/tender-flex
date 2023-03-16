package pl.com.tenderflex.payload.request;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthenticationRequest {

    @NotEmpty(message = "Email cannot be empty")
    private final String email;
    @NotEmpty(message = "Password cannot be empty")
    private final String password;

}