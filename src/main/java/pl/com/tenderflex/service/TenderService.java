package pl.com.tenderflex.service;

import pl.com.tenderflex.model.User;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.request.TenderDetailsRequest;
import pl.com.tenderflex.payload.response.BidderTenderDetailsResponse;
import pl.com.tenderflex.payload.response.ContractorTenderDetailsResponse;
import pl.com.tenderflex.payload.response.TenderInListResponse;

public interface TenderService {

    void createTender(TenderDetailsRequest tenderDetails, User contractor);
    
    Page<TenderInListResponse<Integer>> getContractorPage(Integer contractorId, Integer currentPage, Integer tendersPerPage);

    Page<TenderInListResponse<String>> getBidderPage(Integer bidderId, Integer currentPage, Integer tendersPerPage);

    ContractorTenderDetailsResponse getByIdForContractor(Integer tenderId);
    
    BidderTenderDetailsResponse getByIdForBidder(Integer tenderId, Integer bidderId);

    BidderTenderDetailsResponse getTenderByOfferId(Integer offerId);

}