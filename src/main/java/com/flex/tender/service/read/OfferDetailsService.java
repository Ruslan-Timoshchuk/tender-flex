package com.flex.tender.service.read;

import com.flex.tender.model.Offer;
import com.flex.tender.payload.SummaryPage;
import com.flex.tender.payload.response.ContractorOfferDetailsResponse;
import com.flex.tender.payload.response.OfferSummaryResponse;

public interface OfferDetailsService {

    ContractorOfferDetailsResponse findContractorOfferDetailsById(Integer id);

    Offer findById(Integer id);

    SummaryPage<OfferSummaryResponse> findByContractorWithPagination(Integer contractorId, Integer page,
            Integer pageSize);

}