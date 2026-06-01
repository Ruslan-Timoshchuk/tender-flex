package com.flex.tender.service.details;

import com.flex.tender.model.Tender;
import com.flex.tender.payload.response.BidderTenderDetailsResponse;
import com.flex.tender.payload.response.ContractorTenderDetailsResponse;

public interface TenderDetailsService {

    Tender findById(Integer id);

    ContractorTenderDetailsResponse loadContractortTenderDetailsById(Integer tenderId);

    BidderTenderDetailsResponse loadBidderTenderDetailsById(Integer tenderId);

}