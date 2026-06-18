package com.flex.tender.service.transactional.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.AwardDecision;
import com.flex.tender.model.Contract;
import com.flex.tender.model.FileMetadata;
import com.flex.tender.model.Offer;
import com.flex.tender.model.Tender;
import com.flex.tender.payload.mapper.AwardDecisionMapper;
import com.flex.tender.payload.request.AwardDecisionRequest;
import com.flex.tender.payload.request.AwardOfferDecisionRequest;
import com.flex.tender.payload.response.AwardDecisionResponse;
import com.flex.tender.repository.AwardDecisionRepository;
import com.flex.tender.service.FileStorageService;
import com.flex.tender.service.details.ContractDetailsService;
import com.flex.tender.service.details.OfferDetailsService;
import com.flex.tender.service.details.TenderDetailsService;
import com.flex.tender.service.transactional.AwardDecisionService;
import com.flex.tender.service.transactional.ContractService;
import com.flex.tender.service.transactional.OfferService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AwardDecisionServiceImpl implements AwardDecisionService {

    private final AwardDecisionMapper awardDecisionMapper;
    private final AwardDecisionRepository awardDecisionRepository;
    private final TenderDetailsService tenderDetailsService;
    private final FileStorageService fileStorageService;
    private final OfferDetailsService offerDetailsService;
    private final OfferService offerService;
    private final ContractDetailsService contractDetailsService;
    private final ContractService contractService;
    
    @Override
    public AwardDecisionResponse save(AwardDecisionRequest awardDecisionRequest) {
        AwardDecision awardDecision = awardDecisionMapper.toEntity(awardDecisionRequest);
        Tender tender = tenderDetailsService.findById(awardDecisionRequest.tenderId());
        FileMetadata fileMetadata = fileStorageService.findById(awardDecisionRequest.fileMetadataId());
        awardDecision.setTender(tender);
        awardDecision.setFileMetadata(fileMetadata);
        return awardDecisionMapper.toResponse(awardDecisionRepository.save(awardDecision));
    }

    @Override
    public void applyAwardDecision(AwardOfferDecisionRequest awardOfferDecisionRequest) {
        AwardDecision awardDecision = awardDecisionRepository.findById(awardOfferDecisionRequest.awardDecisionId());
        Offer offer = offerDetailsService.findById(awardOfferDecisionRequest.offerId());
        offerService.applyAwardDecision(offer, awardDecision);
        Contract contract = contractDetailsService.findByAwardDecisionId(awardOfferDecisionRequest.awardDecisionId());
        contractService.initiateContractSigning(contract);
    }

}