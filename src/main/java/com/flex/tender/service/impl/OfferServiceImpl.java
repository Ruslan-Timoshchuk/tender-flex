package com.flex.tender.service.impl;

import static com.flex.tender.model.enumeration.EOfferStatus.*;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import com.flex.tender.model.AwardDecision;
import com.flex.tender.model.CompanyProfile;
import com.flex.tender.model.Offer;
import com.flex.tender.model.RejectDecision;
import com.flex.tender.model.Tender;
import com.flex.tender.model.enumeration.EAuthority;
import com.flex.tender.model.enumeration.EOfferStatus;
import com.flex.tender.payload.Page;
import com.flex.tender.payload.mapstract.OfferMapper;
import com.flex.tender.payload.response.OfferCountResponse;
import com.flex.tender.payload.response.OfferResponse;
import com.flex.tender.payload.response.OfferStatusResponse;
import com.flex.tender.repository.OfferRepository;
import com.flex.tender.service.AuthorityService;
import com.flex.tender.service.CompanyProfileService;
import com.flex.tender.service.OfferService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferMapper offerMapper;
    private final OfferRepository offerRepository;
    private final AuthorityService authorityService;
    private final CompanyProfileService companyProfileService;

    @Override
    public Offer save(Tender tender, Offer offer) {
        offer.setTender(tender);
        CompanyProfile companyProfile = companyProfileService.create(offer.getCompanyProfile());
        offer.setCompanyProfile(companyProfile);
        offer.setGlobalStatus(OFFER_SENT_TO_CONTRACTOR);
        return offerRepository.save(offer);
    }

    @Override
    public Page<OfferResponse> findPageByBidder(Integer bidderId, Integer currentPage, Integer offersPerPage) {
        Integer amountOffersToSkip = (currentPage - 1) * offersPerPage;
        Integer allOffersAmount = offerRepository.countByBidder(bidderId);
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
        Integer allOffersAmount = offerRepository.countByContractor(contractorId);
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
                        if (offer.getGlobalStatus() == OFFER_SENT_TO_CONTRACTOR) {
                            contractorStatus = OFFER_RECEIVED;
                        } else if (offer.getGlobalStatus() == OFFER_SELECTED_BY_CONTRACTOR) {
                            contractorStatus = OFFER_SELECTED;
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
                        if (offer.getGlobalStatus() == OFFER_SENT_TO_CONTRACTOR) {
                            contractorStatus = OFFER_RECEIVED;
                        } else if (offer.getGlobalStatus() == OFFER_SELECTED_BY_CONTRACTOR) {
                            contractorStatus = OFFER_SELECTED;
                        }
                    }
                    return offerMapper.toResponse(offer, contractorStatus, hasAwardDecision(offer),
                            hasRejectDecision(offer));
                }).toList());
    }

    @Override
    public Set<Offer> findAllByTender(Integer tenderId) {
        return offerRepository.findAllByTender(tenderId);
    }

    @Override
    public Offer findById(Integer offerId) {
        return offerRepository.findById(offerId);
    }

    @Override
    public OfferResponse findDetailsByBidder(Integer offerId) {
        Offer offer = offerRepository.findById(offerId);
        return offerMapper.toResponse(offer, offer.getGlobalStatus(), hasAwardDecision(offer),
                hasRejectDecision(offer));
    }
    
    @Override
    public OfferResponse findDetailsByContractor(Integer offerId) {
        Offer offer = offerRepository.findById(offerId);
        EOfferStatus status = offer.getGlobalStatus();
        if (status.equals(EOfferStatus.OFFER_SENT_TO_CONTRACTOR)) {
            status = EOfferStatus.OFFER_RECEIVED;
        } else if (status.equals(EOfferStatus.OFFER_SELECTED_BY_CONTRACTOR)) {
            status = EOfferStatus.OFFER_SELECTED;
        }
        return offerMapper.toResponse(offer, status, hasAwardDecision(offer),
                hasRejectDecision(offer));
    }

    @Override
    public OfferCountResponse countByUserAuthority(Integer userId, Collection<? extends GrantedAuthority> authorities) {
        if (authorityService.hasAuthority(authorities, EAuthority.CONTRACTOR)) {
            return new OfferCountResponse(offerRepository.countByContractor(userId));
        } else if (authorityService.hasAuthority(authorities, EAuthority.BIDDER)) {
            return new OfferCountResponse(offerRepository.countByBidder(userId));
        } else {
            throw new AccessDeniedException("User does not have the required authority to count offers");
        }
    }

    @Override
    public OfferCountResponse countByTender(Integer tenderId) {
        return new OfferCountResponse(offerRepository.countOffersByTender(tenderId));
    }

    @Override
    public Optional<Offer> findOfferByTenderAndBidder(Integer tenderId, Integer userId) {
        return offerRepository.findOfferByTenderAndBidder(tenderId, userId);
    }

    @Override
    public OfferStatusResponse checkOfferStatus(Integer tenderId, Integer userId) {
        return offerRepository.findOfferByTenderAndBidder(tenderId, userId)
                .map(offer -> new OfferStatusResponse(offer.getId(), offer.getGlobalStatus()))
                .orElse(new OfferStatusResponse(0, OFFER_HAS_NOT_SENT));
    }

    @Override
    public Offer selectWinningOffer(Offer offer, AwardDecision awardDecision) {
        offer.setAwardDecision(awardDecision);
        offer.setGlobalStatus(OFFER_SELECTED_BY_CONTRACTOR);
        offerRepository.update(offer);
        return offer;
    }

    @Override
    public Offer rejectOffer(Offer offer, RejectDecision rejectDecision) {
        offer.setRejectDecision(rejectDecision);
        offer.setGlobalStatus(OFFER_REJECTED_BY_CONTRACTOR);
        offerRepository.update(offer);
        return offer;
    }

    @Override
    public Offer rejectUnsuitableOffers(Offer winningOffer, RejectDecision rejectDecision) {
        winningOffer.setGlobalStatus(CONTRACT_APPROVED_BY_BIDDER);
        offerRepository.update(winningOffer);
        offerRepository.findAllByTender(winningOffer.getTender().getId()).stream()
                .filter(offer -> !offer.equals(winningOffer))
                .filter(offer -> !hasAwardDecision(offer))
                .forEach(offerToBeRejected -> {
                    offerToBeRejected.setGlobalStatus(OFFER_REJECTED_BY_CONTRACTOR);
                    offerToBeRejected.setRejectDecision(rejectDecision);
                    offerRepository.update(offerToBeRejected);
                });
        return winningOffer;
    }

    @Override
    public boolean hasAwardDecision(Offer offer) {
        return offer.getAwardDecision() != null && offer.getAwardDecision().getId() != null;
    }

    @Override
    public boolean hasContract(Offer offer) {
        return offer.getContract() != null && offer.getContract().getId() != null;
    }

    @Override
    public boolean hasRejectDecision(Offer offer) {
        return offer.getRejectDecision() != null && offer.getRejectDecision().getId() != null;
    }

    @Override
    public Offer handleOnContractDecline(Offer offer) {
        offer.setGlobalStatus(CONTRACT_DECLINED_BY_BIDDER);
        offerRepository.update(offer);
        return offer;
    }
    
    @Override
    public Offer handleOnSigningDeadlinePassed(Offer offer) {
        offer.setGlobalStatus(OFFER_REJECTED_BY_BIDDER);
        offerRepository.update(offer);
        return offer;
    }

}