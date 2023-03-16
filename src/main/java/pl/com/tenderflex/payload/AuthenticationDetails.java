package pl.com.tenderflex.payload;

import org.springframework.http.ResponseCookie;
import lombok.Data;

@Data
public class AuthenticationDetails {

    private final Integer userId;
    private final String role;
    private final ResponseCookie jwtCookie;

}