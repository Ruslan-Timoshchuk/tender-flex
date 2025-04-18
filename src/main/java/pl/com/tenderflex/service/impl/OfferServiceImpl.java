package pl.com.tenderflex.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.CompanyProfile;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.RejectDecision;
import pl.com.tenderflex.model.enums.EOfferStatus;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.mapstract.OfferMapper;
import pl.com.tenderflex.payload.request.OfferRejectionRequest;
import pl.com.tenderflex.payload.request.OfferRequest;
import pl.com.tenderflex.payload.response.OfferCountResponse;
import pl.com.tenderflex.payload.response.OfferResponse;
import pl.com.tenderflex.payload.response.OfferStatusResponse;
import pl.com.tenderflex.repository.OfferRepository;
import pl.com.tenderflex.service.AwardDecisionService;
import pl.com.tenderflex.service.CompanyProfileService;
import pl.com.tenderflex.service.OfferService;
import pl.com.tenderflex.service.RejectDecisionService;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferMapper offerMapper;
    private final OfferRepository offerRepository;
    private final CompanyProfileService companyProfileService;
    private final AwardDecisionService awardDecisionService;
    private final RejectDecisionService rejectDecisionService;

    @Override
    @Transactional
    public OfferResponse create(OfferRequest offerRequest) {
        Offer offer = offerMapper.toEntity(offerRequest);
        CompanyProfile companyProfile = companyProfileService.create(offer.getCompanyProfile());
        offer.setCompanyProfile(companyProfile);
        offer.setGlobalStatus(EOfferStatus.OFFER_SENT_TO_CONTRACTOR);
        return offerMapper.toResponse(offerRepository.save(offer), offer.getGlobalStatus(), hasAwardDecision(offer),
                hasRejectDecision(offer));
    }

    @Override
    public Page<OfferResponse> findPageByBidder(Integer bidderId, Integer currentPage, Integer offersPerPage) {
        Integer amountOffersToSkip = (currentPage - 1) * offersPerPage;
        Integer allOffersAmount = offerRepository.countOffersByBidder(bidderId);
        Integer totalPages = 1;
        if (allOffersAmount >= offersPerPage) {
            totalPages = allOffersAmount / offersPerPage;
            if (allOffersAmount % offersPerPage > 0) {
                totalPages++;
            }
        }
        return new Page<>(currentPage, totalPages,
                offerRepository.findByBidderWithPagination(bidderId, offersPerPage, amountOffersToSkip).stream()
                        .map(offer -> offerMapper.toResponse(offer, offer.getGlobalStatus(), hasAwardDecision(offer),
                                hasRejectDecision(offer)))
                        .toList());
    }

    @Override
    public Page<OfferResponse> findPageByContractor(Integer contractorId, Integer currentPage, Integer offersPerPage) {
        Integer amountOffersToSkip = (currentPage - 1) * offersPerPage;
        Integer allOffersAmount = offerRepository.countOffersByContractor(contractorId);
        Integer totalPages = 1;
        if (allOffersAmount >= offersPerPage) {
            totalPages = allOffersAmount / offersPerPage;
            if (allOffersAmount % offersPerPage > 0) {
                totalPages++;
            }
        }
        return new Page<>(currentPage, totalPages, offerRepository
                .findByContractorWithPagination(contractorId, offersPerPage, amountOffersToSkip).stream().map(offer -> {
                    EOfferStatus contractorStatus = offer.getGlobalStatus();
                    if (offer.getGlobalStatus() != null) {
                        if (offer.getGlobalStatus() == EOfferStatus.OFFER_SENT_TO_CONTRACTOR) {
                            contractorStatus = EOfferStatus.OFFER_RECEIVED;
                        } else if (offer.getGlobalStatus() == EOfferStatus.OFFER_SELECTED_BY_CONTRACTOR) {
                            contractorStatus = EOfferStatus.OFFER_SELECTED;
                        }
                    }
                    return offerMapper.toResponse(offer, contractorStatus, hasAwardDecision(offer),
                            hasRejectDecision(offer));
                }).toList());
    }

    @Override
    public Page<OfferResponse> findPageByTender(Integer tenderId, Integer currentPage, Integer offersPerPage) {
        Integer amountOffersToSkip = (currentPage - 1) * offersPerPage;
        Integer allOffersAmount = offerRepository.countOffersByTender(tenderId);
        Integer totalPages = 1;
        if (allOffersAmount >= offersPerPage) {
            totalPages = allOffersAmount / offersPerPage;
            if (allOffersAmount % offersPerPage > 0) {
                totalPages++;
            }
        }
        return new Page<>(currentPage, totalPages, offerRepository
                .findByTenderWithPagination(tenderId, offersPerPage, amountOffersToSkip).stream().map(offer -> {
                    EOfferStatus contractorStatus = offer.getGlobalStatus();
                    if (offer.getGlobalStatus() != null) {
                        if (offer.getGlobalStatus() == EOfferStatus.OFFER_SENT_TO_CONTRACTOR) {
                            contractorStatus = EOfferStatus.OFFER_RECEIVED;
                        } else if (offer.getGlobalStatus() == EOfferStatus.OFFER_SELECTED_BY_CONTRACTOR) {
                            contractorStatus = EOfferStatus.OFFER_SELECTED;
                        }
                    }
                    return offerMapper.toResponse(offer, contractorStatus, hasAwardDecision(offer),
                            hasRejectDecision(offer));
                }).toList());
    }

    @Override
    public OfferResponse findById(Integer offerId) {
        Offer offer = offerRepository.findById(offerId);
        return offerMapper.toResponse(offer, offer.getGlobalStatus(), hasAwardDecision(offer),
                hasRejectDecision(offer));
    }

    @Override
    public OfferCountResponse countByBidder(Integer bidderId) {
        return new OfferCountResponse(offerRepository.countOffersByBidder(bidderId));
    }

    @Override
    public OfferCountResponse countByContractor(Integer contractorId) {
        return new OfferCountResponse(offerRepository.countOffersByContractor(contractorId));
    }

    @Override
    public OfferCountResponse countByTender(Integer tenderId) {
        return new OfferCountResponse(offerRepository.countOffersByTender(tenderId));
    }

    @Override
    public OfferStatusResponse checkOfferStatus(Integer userId, Integer tenderId) {
        return offerRepository.findOfferByTenderAndBidder(tenderId, userId)
                .map(offer -> new OfferStatusResponse(offer.getId(), offer.getGlobalStatus()))
                .orElse(new OfferStatusResponse(0, EOfferStatus.OFFER_HAS_NOT_SENT));
    }

    @Override
    public Offer selectWinningOffer(Integer offerId, Integer awardId) {
        Offer offer = offerRepository.findById(offerId);
        offer.setAwardDecision(awardDecisionService.findById(awardId));
        offer.setGlobalStatus(EOfferStatus.OFFER_SELECTED_BY_CONTRACTOR);
        offerRepository.update(offer);
        return offer;
    }

    @Override
    @Transactional
    public OfferResponse rejectOffer(OfferRejectionRequest offerRejectionRequest) {
        Offer offer = offerRepository.findById(offerRejectionRequest.offerId());
        RejectDecision rejectDecision = rejectDecisionService.findById(offerRejectionRequest.rejectId());
        offer.setRejectDecision(rejectDecision);
        offer.setGlobalStatus(EOfferStatus.OFFER_REJECTED_BY_CONTRACTOR);
        offerRepository.update(offer);
        return offerMapper.toResponse(offer, offer.getGlobalStatus(), hasAwardDecision(offer),
                hasRejectDecision(offer));
    }
    
    private Boolean hasAwardDecision(Offer offer) {
        return offer.getAwardDecision() != null && offer.getAwardDecision().getId() != null;
    }

    private Boolean hasRejectDecision(Offer offer) {
        return offer.getRejectDecision() != null && offer.getRejectDecision().getId() != null;
    }
}