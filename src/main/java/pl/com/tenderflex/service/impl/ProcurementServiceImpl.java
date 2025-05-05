package pl.com.tenderflex.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.AwardDecision;
import pl.com.tenderflex.model.Contract;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.RejectDecision;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.payload.mapstract.AwardDecisionMapper;
import pl.com.tenderflex.payload.mapstract.ContractMapper;
import pl.com.tenderflex.payload.mapstract.OfferMapper;
import pl.com.tenderflex.payload.mapstract.RejectDecisionMapper;
import pl.com.tenderflex.payload.mapstract.TenderMapper;
import pl.com.tenderflex.payload.request.AwardOfferRequest;
import pl.com.tenderflex.payload.request.OfferRejectionRequest;
import pl.com.tenderflex.payload.request.OfferSubmissionRequest;
import pl.com.tenderflex.payload.request.ProcurementRequest;
import pl.com.tenderflex.payload.request.SigningContractRequest;
import pl.com.tenderflex.payload.response.AwardResultResponse;
import pl.com.tenderflex.payload.response.OfferRejectionResponse;
import pl.com.tenderflex.payload.response.OfferSubmissionResponse;
import pl.com.tenderflex.payload.response.ProcurementResponse;
import pl.com.tenderflex.payload.response.SigningContractResponse;
import pl.com.tenderflex.service.AwardDecisionService;
import pl.com.tenderflex.service.ContractService;
import pl.com.tenderflex.service.OfferService;
import pl.com.tenderflex.service.ProcurementService;
import pl.com.tenderflex.service.RejectDecisionService;
import pl.com.tenderflex.service.TenderService;

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
    public ProcurementResponse initiateProcurement(ProcurementRequest procurementRequest) {
        Tender tender = tenderService.save(tenderMapper.toEntity(procurementRequest.tender()));
        Contract contract = contractService.save(contractMapper.toEntity(procurementRequest.contract()), tender);
        AwardDecision awardDecision = awardDecisionService
                .save(awardDecisionMapper.toEntity(procurementRequest.awardDecision()), tender);
        RejectDecision rejectDecision = rejectDecisionService
                .save(rejectDecisionMapper.toEntity(procurementRequest.rejectDecision()), tender);
        return new ProcurementResponse(tender.getId(), contract.getId(), awardDecision.getId(), rejectDecision.getId());
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
    public SigningContractResponse approveContract(SigningContractRequest signingContractRequest) {
        Contract contract = contractService.findById(signingContractRequest.contractId());
        contract = contractService.signContract(contract);
        Offer winningOffer = offerService.findById(contract.getOffer().getId());
        RejectDecision rejectDecision = rejectDecisionService.findById(signingContractRequest.rejectId());
        offerService.rejectUnsuitableOffers(winningOffer, rejectDecision);
        tenderService.closeTheTender(tenderService.findById(contract.getTender().getId()));
        return new SigningContractResponse(contract.getId(), contract.isHasSigned());
    }

    @Override
    @Transactional
    public OfferRejectionResponse rejectUnsuitableOffer(OfferRejectionRequest offerRejectionRequest) {
        Offer offer = offerService.findById(offerRejectionRequest.offerId());
        RejectDecision rejectDecision = rejectDecisionService.findById(offerRejectionRequest.rejectId());
        offerService.rejectOffer(offer, rejectDecision);
        return new OfferRejectionResponse(offer.getId(), offer.getGlobalStatus().name());
    }
    
}