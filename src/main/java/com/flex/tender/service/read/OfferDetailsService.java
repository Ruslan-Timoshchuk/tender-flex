package com.flex.tender.service.read;

import com.flex.tender.model.Offer;
import com.flex.tender.payload.SummaryPage;
import com.flex.tender.payload.response.ContractorOfferDetailsResponse;
import com.flex.tender.payload.response.OfferSummaryResponse;
import com.flex.tender.payload.response.TenderOfferSummaryResponse;

public interface OfferDetailsService {

    ContractorOfferDetailsResponse findContractorOfferDetailsById(Integer id);

    Offer findById(Integer id);

    SummaryPage<OfferSummaryResponse> findByContractorWithPagination(Integer contractorId, Integer page,
            Integer pageSize);

    SummaryPage<OfferSummaryResponse> findByBidderWithPagination(Integer bidderId, Integer currentPage,
            Integer offersPerPage);

    SummaryPage<TenderOfferSummaryResponse> findByTenderWithPagination(Integer tenderId, Integer currentPage,
            Integer offersPerPage);

}