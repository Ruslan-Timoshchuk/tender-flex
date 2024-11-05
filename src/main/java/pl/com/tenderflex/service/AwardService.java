package pl.com.tenderflex.service;

import pl.com.tenderflex.payload.iresponse.response.AwardResponse;
import pl.com.tenderflex.payload.request.AwardRequest;

public interface AwardService {

    AwardResponse create(AwardRequest award);
    
}