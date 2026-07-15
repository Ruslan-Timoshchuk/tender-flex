package com.flex.tender.service.write;

import java.time.LocalDate;
import com.flex.tender.model.Tender;
import com.flex.tender.model.embedded.PrincipalSummary;
import com.flex.tender.model.enumeration.ETenderStatus;
import com.flex.tender.payload.request.TenderRequest;

public interface TenderService {

    Tender buildEntity(PrincipalSummary principalSummary, TenderRequest tenderRequest);
    
    Tender save(Tender tender);
    
    Tender close(Tender tender);

    Tender closeIfNoActiveOffers(Tender tender);

    void closeActiveWithExpiredSubmission(ETenderStatus status, LocalDate currentDate);

    void handleOnContractApprove(Tender tender);

}