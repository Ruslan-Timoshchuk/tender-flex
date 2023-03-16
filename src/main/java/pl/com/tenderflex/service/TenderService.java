package pl.com.tenderflex.service;

import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.request.TenderDetailsRequest;
import pl.com.tenderflex.payload.response.BidderTenderResponse;
import pl.com.tenderflex.payload.response.ContractorTenderDetailsResponse;
import pl.com.tenderflex.payload.response.ContractorTenderResponse;

public interface TenderService {

    void createTender(TenderDetailsRequest tenderDetails, Integer contractorId);

    Integer getTendersAmountByContractor(Integer contractorId);
    
    Page<ContractorTenderResponse> getByContractor(Integer contractorId, Integer currentPage);

    Page<BidderTenderResponse> getByCondition(Integer currentPage);

    ContractorTenderDetailsResponse getById(Integer tenderId);

}