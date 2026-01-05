package com.flex.tender.service;

import com.flex.tender.payload.AuthenticationDetails;
import com.flex.tender.payload.request.AuthenticationRequest;

public interface AuthenticationService {

    AuthenticationDetails authenticate(AuthenticationRequest request);

}