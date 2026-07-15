package com.flex.tender.service.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.AwardDecision;
import com.flex.tender.model.Contract;
import com.flex.tender.model.Offer;
import com.flex.tender.model.Tender;
import com.flex.tender.payload.request.ApproveContractDecisionRequest;
import com.flex.tender.payload.request.AwardOfferDecisionRequest;
import com.flex.tender.payload.request.DeclineContractDecisionRequest;
import com.flex.tender.service.facade.AwardDecisionManager;
import com.flex.tender.service.read.AwardDecisionDetailsService;
import com.flex.tender.service.read.ContractDetailsService;
import com.flex.tender.service.read.OfferDetailsService;
import com.flex.tender.service.read.TenderDetailsService;
import com.flex.tender.service.write.ContractService;
import com.flex.tender.service.write.OfferService;
import com.flex.tender.service.write.TenderService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AwardDecisionManagerImpl implements AwardDecisionManager {

    private final AwardDecisionDetailsService awardDecisionDetailsService;
    private final OfferDetailsService offerDetailsService;
    private final OfferService offerService;
    private final ContractDetailsService contractDetailsService;
    private final ContractService contractService;
    private final TenderDetailsService tenderDetailsService;
    private final TenderService tenderService;
    
    @Override
    public void applyAwardDecision(AwardOfferDecisionRequest awardOfferDecisionRequest) {
        AwardDecision awardDecision = awardDecisionDetailsService.findById(awardOfferDecisionRequest.awardDecisionId());
        Offer offer = offerDetailsService.findById(awardOfferDecisionRequest.offerId());
        offerService.applyAwardDecision(offer, awardDecision);
        Contract contract = contractDetailsService.findByAwardDecisionId(awardDecision.getId());
        contractService.initiateContractSigning(contract);
    }

    @Override
    public void declineContract(DeclineContractDecisionRequest declineContractDecisionRequest) {
        AwardDecision awardDecision = awardDecisionDetailsService.findById(declineContractDecisionRequest.awardDecisionId());
        Offer offer = offerDetailsService.findById(declineContractDecisionRequest.offerId());
        offerService.handleOnContractDecline(offer);
        Contract contract = contractDetailsService.findByAwardDecisionId(awardDecision.getId());
        contractService.decline(contract);
    }

    @Override
    public void approveContract(ApproveContractDecisionRequest approveContractDecisionRequest) {  
        AwardDecision awardDecision = awardDecisionDetailsService.findById(approveContractDecisionRequest.awardDecisionId());
        Offer offer = offerDetailsService.findById(approveContractDecisionRequest.offerId());
        offerService.handleOnContractApprove(offer);
        Contract contract = contractDetailsService.findByAwardDecisionId(awardDecision.getId());
        contractService.sign(contract);
        Tender tender = tenderDetailsService.findById(approveContractDecisionRequest.tenderId());
        tenderService.handleOnContractApprove(tender);
    }
    
}