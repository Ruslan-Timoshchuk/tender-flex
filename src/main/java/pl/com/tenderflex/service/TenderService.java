package pl.com.tenderflex.service;

import pl.com.tenderflex.dto.TenderDetailsRequest;

public interface TenderService {

    void createTender(TenderDetailsRequest tenderDetails, Integer contractorId);

}