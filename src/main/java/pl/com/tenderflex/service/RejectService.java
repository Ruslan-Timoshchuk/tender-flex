package pl.com.tenderflex.service;

import pl.com.tenderflex.payload.iresponse.response.RejectResponse;
import pl.com.tenderflex.payload.request.RejectRequest;

public interface RejectService {

    RejectResponse create(RejectRequest rejectRequest);

}