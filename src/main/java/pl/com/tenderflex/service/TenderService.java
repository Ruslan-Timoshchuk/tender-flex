package pl.com.tenderflex.service;

import pl.com.tenderflex.dto.Attachment;
import pl.com.tenderflex.dto.TenderDetails;

public interface TenderService {

    void createTender(Attachment attachment, TenderDetails tenderDetails, Integer contractorId);

}