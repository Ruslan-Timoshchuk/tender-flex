package com.flex.tender.service.write.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.AwardDecision;
import com.flex.tender.model.Contract;
import com.flex.tender.model.FileMetadata;
import com.flex.tender.model.Offer;
import com.flex.tender.payload.mapper.AwardDecisionMapper;
import com.flex.tender.payload.request.AwardDecisionRequest;
import com.flex.tender.payload.request.AwardOfferDecisionRequest;
import com.flex.tender.repository.AwardDecisionRepository;
import com.flex.tender.service.FileStorageService;
import com.flex.tender.service.read.ContractDetailsService;
import com.flex.tender.service.read.OfferDetailsService;
import com.flex.tender.service.write.AwardDecisionService;
import com.flex.tender.service.write.ContractService;
import com.flex.tender.service.write.OfferService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AwardDecisionServiceImpl implements AwardDecisionService {

    private final AwardDecisionMapper awardDecisionMapper;
    private final AwardDecisionRepository awardDecisionRepository;
    private final FileStorageService fileStorageService;
    private final OfferDetailsService offerDetailsService;
    private final OfferService offerService;
    private final ContractDetailsService contractDetailsService;
    private final ContractService contractService;
    
    @Override
    public AwardDecision buildEntity(AwardDecisionRequest awardDecisionRequest) {
        AwardDecision awardDecision = awardDecisionMapper.toEntity(awardDecisionRequest);
        FileMetadata awardDecisionFile = fileStorageService.findById(awardDecisionRequest.fileMetadataId());
        awardDecision.setFileMetadata(awardDecisionFile);
        return awardDecision;
    }
    
    @Override
    public AwardDecision save(AwardDecision awardDecision) {
        return awardDecisionRepository.save(awardDecision);
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