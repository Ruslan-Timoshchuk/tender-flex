package com.flex.tender.service.read.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.Offer;
import com.flex.tender.model.enumeration.EOfferStatus;
import com.flex.tender.repository.OfferRepository;
import com.flex.tender.service.read.OfferDetailsBatchService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OfferDetailsBatchServiceImpl implements OfferDetailsBatchService {

    private final OfferRepository offerRepository;
    
    @Override
    public boolean existsByTenderIdAndGlobalStatusIn(Integer tenderId, List<EOfferStatus> statuses) {
        return offerRepository.existsByTenderIdAndGlobalStatusIn(tenderId, statuses);
    }

    @Override
    public Map<Integer, Offer> findByBidderIdAndTenderIdIn(Integer userId, List<Integer> tenderIds) {
        return offerRepository.findByBidderIdAndTenderIdIn(userId, tenderIds);
    }
    
    @Override
    public Map<Integer, Integer> countOffersByTenderIds(List<Integer> tenderIds) {
        return offerRepository.countByTenderIdIn(tenderIds);
    }
    
}