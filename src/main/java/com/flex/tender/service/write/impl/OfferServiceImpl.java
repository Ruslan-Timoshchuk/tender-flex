package com.flex.tender.service.write.impl;

import static com.flex.tender.model.enumeration.EOfferStatus.*;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.AwardDecision;
import com.flex.tender.model.Offer;
import com.flex.tender.model.RejectDecision;
import com.flex.tender.model.Tender;
import com.flex.tender.model.embedded.PrincipalSummary;
import com.flex.tender.payload.mapper.OfferMapper;
import com.flex.tender.payload.request.OfferRequest;
import com.flex.tender.payload.response.OfferSummaryResponse;
import com.flex.tender.repository.OfferRepository;
import com.flex.tender.service.read.CurrencyDetailsService;
import com.flex.tender.service.read.FileStorageService;
import com.flex.tender.service.read.TenderDetailsService;
import com.flex.tender.service.read.CustomUserDetailsService;
import com.flex.tender.service.write.OfferService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferMapper offerMapper;
    private final CustomUserDetailsService userService;
    private final TenderDetailsService tenderDetailsService;
    private final OfferRepository offerRepository;
    private final CurrencyDetailsService currencyService;
    private final FileStorageService fileStorageService;

    @Override
    public Offer buildEntity(PrincipalSummary principalSummary, OfferRequest offerRequest) {
        Offer offer = offerMapper.toEntity(offerRequest);
        offer.setBidder(userService.findById(principalSummary.userId()));
        Tender tender = tenderDetailsService.findById(offerRequest.tenderId());
        offer.setTender(tender);
        offer.setCurrency(currencyService.findById(offerRequest.currencyId()));
        offer.setGlobalStatus(SENT);
        offer.setProposition(fileStorageService.findById(offerRequest.propositionMetadataId()));
        return offer;
    }
    
    @Override
    public OfferSummaryResponse save(Offer offer) {
        return offerMapper.toBidderSummaryResponse(offerRepository.save(offer), offer.getTender());
    }

    @Override
    public Offer applyAwardDecision(Offer offer, AwardDecision awardDecision) {
        offer.setAwardDecision(awardDecision);
        offer.setGlobalStatus(SELECTED);
        offerRepository.update(offer);
        return offer;
    }

    @Override
    public Offer applyRejectDecision(Offer offer, RejectDecision rejectDecision) {
        offer.setRejectDecision(rejectDecision);
        offer.setGlobalStatus(REJECTED_BY_CONTRACTOR);
        offerRepository.update(offer);
        return offer;
    }

    @Override
    public Offer rejectUnsuitableOffers(Offer winningOffer, RejectDecision rejectDecision) {
        winningOffer.setGlobalStatus(CONTRACT_APPROVED);
        offerRepository.update(winningOffer);
        offerRepository.findByTenderIdAndGlobalStatusIn(winningOffer.getTender().getId(), List.of(SENT, SELECTED))
                .stream().forEach(activeOffer -> {
                    activeOffer.setGlobalStatus(REJECTED_BY_BIDDER);
                    activeOffer.setRejectDecision(rejectDecision);
                    offerRepository.update(activeOffer);
                });
        return winningOffer;
    }

    @Override
    public Offer handleOnContractDecline(Offer offer) {
        offer.setGlobalStatus(CONTRACT_DECLINED);
        offerRepository.update(offer);
        return offer;
    }
    
    @Override
    public Offer handleOnContractApprove(Offer offer) {
        offer.setGlobalStatus(CONTRACT_APPROVED);
        offerRepository.update(offer);
        return offer;
    }
    
    @Override
    public Offer handleOnSigningDeadlinePassed(Offer offer) {
        offer.setGlobalStatus(REJECTED_BY_BIDDER);
        offerRepository.update(offer);
        return offer;
    }

}