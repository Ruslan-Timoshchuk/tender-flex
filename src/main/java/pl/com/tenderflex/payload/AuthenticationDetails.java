package pl.com.tenderflex.payload;

import org.springframework.http.ResponseCookie;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthenticationDetails {

    private final Integer userId;
    private final String role;
    private final ResponseCookie jwtCookie;

}