package pl.com.tenderflex.service;

import pl.com.tenderflex.payload.request.TenderDetailsRequest;

public interface TenderService {

    void createTender(TenderDetailsRequest tenderDetails, Integer contractorId);

}