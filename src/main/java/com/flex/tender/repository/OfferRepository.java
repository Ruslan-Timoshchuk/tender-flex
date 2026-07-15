package com.flex.tender.repository;

import java.util.List;
import java.util.Map;
import com.flex.tender.model.Offer;
import com.flex.tender.model.enumeration.EOfferStatus;

public interface OfferRepository {

    Offer save(Offer offer);
    
    void update(Offer offer);
    
    List<Offer> findByBidderWithPagination(Integer bidderId, Integer limit, Integer offset);
    
    List<Offer> findByContractorWithPagination(Integer contractorId, Integer limit, Integer offset);
    
    List<Offer> findByTenderWithPagination(Integer tenderId, Integer limit, Integer offset);
    
    Integer countByBidder(Integer bidderId);
    
    Integer countByContractor(Integer contractorId);
    
    Integer countAllByTender(Integer tenderId);
    
    Map<Integer, Integer> countByTenderIdIn(List<Integer> tenderIds);
    
    Map<Integer, Offer> findByBidderIdAndTenderIdIn(Integer bidderId, List<Integer> tenderIds);
        
    Offer findById(Integer offerId);

    List<Offer> findByTenderIdAndGlobalStatusIn(Integer id, List<EOfferStatus> statuses);

    boolean existsByTenderIdAndGlobalStatusIn(Integer tenderId, List<EOfferStatus> statuses);

}