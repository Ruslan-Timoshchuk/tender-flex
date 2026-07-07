package com.flex.tender.service.facade;

import com.flex.tender.model.embedded.PrincipalSummary;
import com.flex.tender.payload.request.TenderRequest;
import com.flex.tender.payload.response.ContractorTenderSummaryResponse;

public interface TenderManager {

    ContractorTenderSummaryResponse save(PrincipalSummary principalSummary, TenderRequest tenderRequest);

}