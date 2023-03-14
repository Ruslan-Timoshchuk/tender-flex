package pl.com.tenderflex.payload.request;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationRequest {

    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @NotEmpty(message = "Password cannot be empty")
    private String password;

}