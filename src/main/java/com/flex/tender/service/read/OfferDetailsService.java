package com.flex.tender.service.read;

import java.util.List;
import java.util.Map;

import com.flex.tender.model.Offer;
import com.flex.tender.model.enumeration.EOfferStatus;
import com.flex.tender.payload.SummaryPage;
import com.flex.tender.payload.response.BidderOfferDetailsResponse;
import com.flex.tender.payload.response.ContractorOfferDetailsResponse;
import com.flex.tender.payload.response.OfferCountResponse;
import com.flex.tender.payload.response.OfferSummaryResponse;
import com.flex.tender.payload.response.TenderOfferSummaryResponse;

public interface OfferDetailsService {

    BidderOfferDetailsResponse findBidderOfferDetailsById(Integer id);
    
    ContractorOfferDetailsResponse findContractorOfferDetailsById(Integer id);
    
    Offer findById(Integer id);

    SummaryPage<OfferSummaryResponse> findByContractorWithPagination(Integer contractorId, Integer page,
            Integer pageSize);

    SummaryPage<OfferSummaryResponse> findByBidderWithPagination(Integer bidderId, Integer currentPage,
            Integer offersPerPage);

    SummaryPage<TenderOfferSummaryResponse> findByTenderWithPagination(Integer tenderId, Integer currentPage,
            Integer offersPerPage);

    boolean existsByTenderIdAndGlobalStatusIn(Integer tenderId, List<EOfferStatus> statuses);

    OfferCountResponse countByBidder(Integer bidderId);

    OfferCountResponse countByContractor(Integer contractorId);

    Map<Integer, Offer> findByBidderIdAndTenderIdIn(Integer userId, List<Integer> tenderIds);

    Map<Integer, Integer> countOffersByTenderIds(List<Integer> tenderIds);

}