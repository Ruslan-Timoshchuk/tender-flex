package com.flex.tender.service;

import pl.com.tenderflex.payload.AuthenticationDetails;
import pl.com.tenderflex.payload.request.AuthenticationRequest;

public interface AuthenticationService {

    AuthenticationDetails authenticate(AuthenticationRequest request);

}