package pl.com.tenderflex.service;

import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.request.TenderDetailsRequest;
import pl.com.tenderflex.payload.response.BidderTenderDetailsResponse;
import pl.com.tenderflex.payload.response.BidderTenderResponse;
import pl.com.tenderflex.payload.response.ContractorTenderDetailsResponse;
import pl.com.tenderflex.payload.response.ContractorTenderResponse;
import pl.com.tenderflex.payload.response.TotalResponse;

public interface TenderService {

    void createTender(TenderDetailsRequest tenderDetails, Integer contractorId);

    TotalResponse getTotalTendersAndOffersByContractor(Integer contractorId);
    
    Page<ContractorTenderResponse> getByContractor(Integer contractorId, Integer currentPage);

    Page<BidderTenderResponse> getByCondition(Integer currentPage);

    ContractorTenderDetailsResponse getByIdForContractor(Integer tenderId);
    
    BidderTenderDetailsResponse getByIdForBidder(Integer tenderId);

}