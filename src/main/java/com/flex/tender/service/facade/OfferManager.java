package com.flex.tender.service.facade;

import com.flex.tender.model.embedded.PrincipalSummary;
import com.flex.tender.payload.request.OfferRequest;
import com.flex.tender.payload.response.OfferSummaryResponse;

public interface OfferManager {

    OfferSummaryResponse save(PrincipalSummary principalSummary, OfferRequest offerRequest);

}
