package pl.com.tenderflex.service;

import java.util.List;
import pl.com.tenderflex.dto.Attachment;
import pl.com.tenderflex.dto.TenderDetailsRequest;
import pl.com.tenderflex.dto.TenderDetailsResponse;

public interface TenderService {

    void createTender(Attachment attachment, TenderDetailsRequest tenderDetails, Integer contractorId);
    
    List<TenderDetailsResponse> getByContractor(Integer contractorId, Integer currentTendersAmount, Integer tendersToSkip);

}