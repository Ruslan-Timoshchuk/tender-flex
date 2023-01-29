package pl.com.tenderflex.service;

import java.util.List;

import pl.com.tenderflex.dto.Attachment;
import pl.com.tenderflex.dto.TenderDetails;
import pl.com.tenderflex.model.Tender;

public interface TenderService {

    void createTender(Attachment attachment, TenderDetails tenderDetails, Integer contractorId);
    
    List<Tender> getByContractor(Integer contractorId, Integer currentTendersAmount, Integer tendersToSkip);

}