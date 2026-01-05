package com.flex.tender.controller;

import static com.flex.tender.controller.constant.AuthenticationUrls.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flex.tender.payload.AuthenticationDetails;
import com.flex.tender.payload.request.AuthenticationRequest;
import com.flex.tender.payload.response.AuthenticationResponse;
import com.flex.tender.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTHENTICATION_MAIN)
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(USER_LOGIN)
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid final AuthenticationRequest authenticationRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadCredentialsException("Email and password should not be empty");
        }
        AuthenticationDetails authenticationDetails = authenticationService.authenticate(authenticationRequest);
        ResponseCookie jwtCookie = authenticationDetails.getJwtCookie();
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(authenticationDetails.getUserId(),
                authenticationDetails.getAuthorities());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(authenticationResponse);
    }

}