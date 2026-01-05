package com.flex.tender.payload;

import java.util.List;
import org.springframework.http.ResponseCookie;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthenticationDetails {

    private final Integer userId;
    private final List<String> authorities;
    private final ResponseCookie jwtCookie;

}