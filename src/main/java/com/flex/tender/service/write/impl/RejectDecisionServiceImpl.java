package com.flex.tender.service.transactional.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.Offer;
import com.flex.tender.model.RejectDecision;
import com.flex.tender.payload.mapper.RejectDecisionMapper;
import com.flex.tender.payload.request.RejectDecisionRequest;
import com.flex.tender.payload.request.RejectOfferDecisionRequest;
import com.flex.tender.repository.RejectDecisionRepository;
import com.flex.tender.service.FileStorageService;
import com.flex.tender.service.details.OfferDetailsService;
import com.flex.tender.service.transactional.OfferService;
import com.flex.tender.service.transactional.RejectDecisionService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RejectDecisionServiceImpl implements RejectDecisionService {

    private final RejectDecisionMapper rejectDecisionMapper;
    private final FileStorageService fileStorageService;
    private final OfferDetailsService offerDetailsService;
    private final OfferService offerService;
    private final RejectDecisionRepository rejectDecisionRepository;

    @Override
    public RejectDecision buildEntity(RejectDecisionRequest rejectDecisionRequest) {
        RejectDecision rejectDecision = rejectDecisionMapper.toEntity(rejectDecisionRequest);
        rejectDecision.setFileMetadata(fileStorageService.findById(rejectDecisionRequest.fileMetadataId()));
        return rejectDecision;
    }
    
    @Override
    public RejectDecision save(RejectDecision rejectDecision) {
        return rejectDecisionRepository.save(rejectDecision);
    }

    @Override
    public void applyRejectDecision(RejectOfferDecisionRequest rejectOfferDecisionRequest) {
        RejectDecision rejectDecision = rejectDecisionRepository.findById(rejectOfferDecisionRequest.rejectDecisionId());
        Offer offer = offerDetailsService.findById(rejectOfferDecisionRequest.offerId());
        offerService.applyRejectDecision(offer, rejectDecision);
    }

}