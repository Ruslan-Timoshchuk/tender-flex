package com.flex.tender.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flex.tender.model.AwardDecision;
import com.flex.tender.model.Contract;
import com.flex.tender.model.Offer;
import com.flex.tender.model.RejectDecision;
import com.flex.tender.model.Tender;
import com.flex.tender.payload.mapper.AwardDecisionMapper;
import com.flex.tender.payload.mapper.ContractMapper;
import com.flex.tender.payload.mapper.OfferMapper;
import com.flex.tender.payload.mapper.RejectDecisionMapper;
import com.flex.tender.payload.mapper.TenderMapper;
import com.flex.tender.payload.request.AwardOfferRequest;
import com.flex.tender.payload.request.InitiateProcurementRequest;
import com.flex.tender.payload.request.OfferRejectionRequest;
import com.flex.tender.payload.request.OfferSubmissionRequest;
import com.flex.tender.payload.request.ProcurementCompletionRequest;
import com.flex.tender.payload.request.ProcurementRejectionRequest;
import com.flex.tender.payload.response.AwardResultResponse;
import com.flex.tender.payload.response.OfferRejectionResponse;
import com.flex.tender.payload.response.OfferSubmissionResponse;
import com.flex.tender.payload.response.ProcurementCompletionResponse;
import com.flex.tender.payload.response.ProcurementInitiationResponse;
import com.flex.tender.payload.response.ProcurementRejectionResponse;
import com.flex.tender.service.AwardDecisionService;
import com.flex.tender.service.ContractService;
import com.flex.tender.service.OfferService;
import com.flex.tender.service.ProcurementService;
import com.flex.tender.service.RejectDecisionService;
import com.flex.tender.service.TenderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcurementServiceImpl implements ProcurementService {

    private final TenderService tenderService;
    private final TenderMapper tenderMapper;
    private final ContractService contractService;
    private final ContractMapper contractMapper;
    private final AwardDecisionService awardDecisionService;
    private final AwardDecisionMapper awardDecisionMapper;
    private final RejectDecisionService rejectDecisionService;
    private final RejectDecisionMapper rejectDecisionMapper;
    private final OfferService offerService;
    private final OfferMapper offerMapper;

    @Override
    @Transactional
    public ProcurementInitiationResponse initiateProcurement(InitiateProcurementRequest initiateProcurementRequest) {
        Tender tender = tenderService.save(tenderMapper.toEntity(initiateProcurementRequest.tender()));
        Contract contract = contractService.save(contractMapper.toEntity(initiateProcurementRequest.contract()),
                tender);
        AwardDecision awardDecision = awardDecisionService
                .save(awardDecisionMapper.toEntity(initiateProcurementRequest.awardDecision()), tender);
        RejectDecision rejectDecision = rejectDecisionService
                .save(rejectDecisionMapper.toEntity(initiateProcurementRequest.rejectDecision()), tender);
        return new ProcurementInitiationResponse(tender.getId(), contract.getId(), awardDecision.getId(),
                rejectDecision.getId());
    }

    @Override
    @Transactional
    public OfferSubmissionResponse sendNewOffer(OfferSubmissionRequest offerSubmissionRequest) {
        Tender tender = tenderService.findById(offerSubmissionRequest.tenderId());
        Offer offer = offerMapper.toEntity(offerSubmissionRequest.offer());
        offer = offerService.save(tender, offer);
        return new OfferSubmissionResponse(offer.getId());
    }

    @Override
    @Transactional
    public AwardResultResponse makeAnAwardDecision(AwardOfferRequest awardOfferRequest) {
        Offer offer = offerService.selectWinningOffer(offerService.findById(awardOfferRequest.offerId()),
                awardDecisionService.findById(awardOfferRequest.awardId()));
        Contract contract = contractService
                .initiateContractSigning(contractService.findById(awardOfferRequest.contractId()), offer);
        return new AwardResultResponse(contract.getId(), offer.getAwardDecision().getId(), offer.getId(),
                offer.getGlobalStatus().name());
    }

    @Override
    @Transactional
    public ProcurementCompletionResponse completeProcurement(
            ProcurementCompletionRequest procurementCompletionRequest) {
        Contract contract = contractService.findById(procurementCompletionRequest.contractId());
        contract = contractService.sign(contract);
        Offer winningOffer = offerService.findById(contract.getOffer().getId());
        RejectDecision rejectDecision = rejectDecisionService.findById(procurementCompletionRequest.rejectId());
        winningOffer = offerService.rejectUnsuitableOffers(winningOffer, rejectDecision);
        tenderService.close(tenderService.findById(contract.getTender().getId()));
        return new ProcurementCompletionResponse(contract.getId(), contract.getGlobalStatus().name(),
                winningOffer.getGlobalStatus().name());
    }

    @Override
    @Transactional
    public OfferRejectionResponse rejectUnsuitableOffer(OfferRejectionRequest offerRejectionRequest) {
        Offer offer = offerService.findById(offerRejectionRequest.offerId());
        RejectDecision rejectDecision = rejectDecisionService.findById(offerRejectionRequest.rejectId());
        offerService.rejectOffer(offer, rejectDecision);
        return new OfferRejectionResponse(offer.getId(), offer.getGlobalStatus().name());
    }

    @Override
    @Transactional
    public ProcurementRejectionResponse rejectProcurement(ProcurementRejectionRequest procurementRejectionRequest) {
        Contract contract = contractService.findById(procurementRejectionRequest.contractId());
        Tender tender = tenderService.findById(contract.getTender().getId());
        Offer offer = offerService.findById(contract.getOffer().getId());        
        contractService.decline(contract);
        offer = offerService.handleOnContractDecline(offer);
        tender = tenderService.closeIfNoActiveOffers(tender);  
        return new ProcurementRejectionResponse(tender.getGlobalStatus().name(), offer.getGlobalStatus().name());
    }

}