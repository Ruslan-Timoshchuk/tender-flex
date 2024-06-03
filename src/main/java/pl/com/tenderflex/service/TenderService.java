package pl.com.tenderflex.service;

import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.request.TenderDetailsRequest;
import pl.com.tenderflex.payload.response.BidderTenderDetailsResponse;
import pl.com.tenderflex.payload.response.BidderTenderResponse;
import pl.com.tenderflex.payload.response.ContractorTenderDetailsResponse;
import pl.com.tenderflex.payload.response.ContractorTenderResponse;
import pl.com.tenderflex.security.impl.UserDetailsImpl;

public interface TenderService {

    void createTender(TenderDetailsRequest tenderDetails, UserDetailsImpl contractor);
    
    Page<ContractorTenderResponse> getByContractor(Integer contractorId, Integer currentPage, Integer tendersPerPage);

    Page<BidderTenderResponse> getByBidder(Integer bidderId, Integer currentPage, Integer tendersPerPage);

    ContractorTenderDetailsResponse getByIdForContractor(Integer tenderId);
    
    BidderTenderDetailsResponse getByIdForBidder(Integer tenderId, Integer bidderId);

    BidderTenderDetailsResponse getTenderByOfferId(Integer offerId);

}