package com.flex.tender.service.read;

import java.util.List;
import java.util.Map;
import com.flex.tender.model.Tender;
import com.flex.tender.payload.response.BidderTenderDetailsResponse;
import com.flex.tender.payload.response.ContractorTenderDetailsResponse;

public interface TenderDetailsService {

    Tender findById(Integer id);

    ContractorTenderDetailsResponse loadContractortTenderDetailsById(Integer tenderId);

    BidderTenderDetailsResponse loadBidderTenderDetailsById(Integer tenderId);

    Map<Integer, Tender> findByOfferIdIn(List<Integer> offerIds);

}