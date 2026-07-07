package com.flex.tender.service.read.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.Tender;
import com.flex.tender.repository.TenderRepository;
import com.flex.tender.service.read.TenderDetailsBatchService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TenderDetailsBatchServiceImpl implements TenderDetailsBatchService {

    private final TenderRepository tenderRepository;
  
    @Override
    public Map<Integer, Tender> findByOfferIdIn(List<Integer> offerIds) {
        return tenderRepository.findByOfferIdIn(offerIds);
    }

}