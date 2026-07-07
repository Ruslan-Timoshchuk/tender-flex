package com.flex.tender.service.read;

import com.flex.tender.model.Tender;
import com.flex.tender.payload.SummaryPage;
import com.flex.tender.payload.response.BidderTenderSummaryResponse;
import com.flex.tender.payload.response.ContractorTenderSummaryResponse;
import com.flex.tender.payload.response.TenderCountResponse;
import com.flex.tender.payload.response.TenderDetailsResponse;

public interface TenderDetailsService {

    Tender findById(Integer id);

    TenderDetailsResponse loadTenderDetailsById(Integer tenderId);

    SummaryPage<ContractorTenderSummaryResponse> findByContractorWithPagination(Integer userId, Integer currentPage,
            Integer tendersPerPage);

    SummaryPage<BidderTenderSummaryResponse> findByBidderWithPagination(Integer bidderId, Integer currentPage,
            Integer tendersPerPage);

    TenderCountResponse countByContractor(Integer contractorId);

    TenderCountResponse countAll();

}