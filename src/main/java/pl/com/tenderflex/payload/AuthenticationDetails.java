package pl.com.tenderflex.payload;

import org.springframework.http.ResponseCookie;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AuthenticationDetails {

    private final Integer userId;
    private final String role;
    private final ResponseCookie jwtCookie;

}