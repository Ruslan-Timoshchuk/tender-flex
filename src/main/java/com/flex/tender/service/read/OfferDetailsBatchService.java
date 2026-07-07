package com.flex.tender.service.read;

import java.util.List;
import java.util.Map;
import com.flex.tender.model.Offer;
import com.flex.tender.model.enumeration.EOfferStatus;

public interface OfferDetailsBatchService {

    boolean existsByTenderIdAndGlobalStatusIn(Integer tenderId, List<EOfferStatus> statuses);

    Map<Integer, Offer> findByBidderIdAndTenderIdIn(Integer userId, List<Integer> tenderIds);

    Map<Integer, Integer> countOffersByTenderIds(List<Integer> tenderIds);

}