package com.flex.tender.service;

import java.time.LocalDate;
import com.flex.tender.model.Tender;
import com.flex.tender.model.enumeration.ETenderStatus;
import com.flex.tender.payload.Page;
import com.flex.tender.payload.response.BidderTenderSummaryResponse;
import com.flex.tender.payload.response.ContractorTenderSummaryResponse;
import com.flex.tender.payload.response.TenderCountResponse;
import com.flex.tender.payload.response.TenderResponse;

public interface TenderService {

    Tender save(Tender tender);

    Tender findById(Integer id);

    TenderResponse findDetailsById(Integer id);

    TenderCountResponse countByContractor(Integer contractorId);
    
    TenderCountResponse countAll();

    Page<ContractorTenderSummaryResponse> findByContractorWithPagination(Integer userId, Integer currentPage, Integer tendersPerPage);

    Page<BidderTenderSummaryResponse> findByBidderWithPagination(Integer userId, Integer currentPage, Integer tendersPerPage);

    Tender close(Tender tender);

    Tender closeIfNoActiveOffers(Tender tender);

    void closeActiveWithExpiredSubmission(ETenderStatus status, LocalDate currentDate);

}