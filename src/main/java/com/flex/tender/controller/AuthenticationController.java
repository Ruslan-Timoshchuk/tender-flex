package com.flex.tender.controller;

import static com.flex.tender.controller.constant.AuthenticationUrls.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flex.tender.model.AuthenticatedPrincipal;
import com.flex.tender.model.JwtAuthenticationToken;
import com.flex.tender.payload.request.AuthenticationRequest;
import com.flex.tender.payload.response.AuthenticationResponse;
import com.flex.tender.service.AuthenticationService;
import com.flex.tender.service.JwtTokenGenerator;
import com.flex.tender.service.JwtCookiesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTHENTICATION_MAIN)
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final JwtCookiesService jwtCookiesService;

    @PostMapping(USER_LOGIN)
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid final AuthenticationRequest credential, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadCredentialsException("Email and password should not be empty");
        }
        final AuthenticatedPrincipal authenticatedPrincipal = authenticationService.authenticate(credential);
        final JwtAuthenticationToken authenticationToken = jwtTokenGenerator
                .issueAuthenticationToken(authenticatedPrincipal);
        final HttpHeaders headers = jwtCookiesService.issueJwtCookie(authenticationToken);
        final AuthenticationResponse authenticationResponse = authenticationService
                .resolveAuthenticationResponse(authenticatedPrincipal);
        return ResponseEntity.ok().headers(headers).body(authenticationResponse);
    }

}