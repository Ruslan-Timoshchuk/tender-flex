package pl.com.tenderflex.service;

import pl.com.tenderflex.dto.Attachment;
import pl.com.tenderflex.dto.TenderDetailsRequest;

public interface TenderService {

    void createTender(Attachment attachment, TenderDetailsRequest tenderDetails, Integer contractorId);

}