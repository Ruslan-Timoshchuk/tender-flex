package com.flex.tender.controller;

import java.util.UUID;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flex.tender.model.embedded.PrincipalDetails;
import com.flex.tender.model.embedded.JwtAuthenticationToken;
import com.flex.tender.payload.request.AuthenticationRequest;
import com.flex.tender.payload.response.AuthenticationResponse;
import com.flex.tender.service.AuthenticationService;
import com.flex.tender.service.JwtTokenGenerator;
import com.flex.tender.service.JwtCookiesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base.path}/${api.v1}/${api.authentication.path}")
public class AuthenticationController {

    public static final String URL_USER_LOGIN = "/login";
    public static final String URL_LOAD_AUTHENTICATION_STATE = "/load-authentication-state";
    
    private final AuthenticationService authenticationService;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final JwtCookiesService jwtCookiesService;

    @PostMapping(URL_USER_LOGIN)
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid final AuthenticationRequest credential, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadCredentialsException("Email and password should not be empty");
        }
        final PrincipalDetails principalDetails = authenticationService.authenticate(credential);
        final JwtAuthenticationToken authenticationToken = jwtTokenGenerator
                .issueAuthenticationToken(principalDetails);
        final HttpHeaders headers = jwtCookiesService.issueJwtCookie(authenticationToken);
        final AuthenticationResponse authenticationResponse = authenticationService
                .resolveAuthenticationResponse(principalDetails, authenticationToken.principalUuid());
        return ResponseEntity
                   .ok()
                   .headers(headers)
                   .body(authenticationResponse);
    }

    @GetMapping(URL_LOAD_AUTHENTICATION_STATE)
    public ResponseEntity<AuthenticationResponse> loadAuthenticationState(
            @AuthenticationPrincipal(expression = "principalUuid") UUID principalUuid) {
        return ResponseEntity
                   .ok(authenticationService.loadAuthenticationState(principalUuid));
    }
    
}