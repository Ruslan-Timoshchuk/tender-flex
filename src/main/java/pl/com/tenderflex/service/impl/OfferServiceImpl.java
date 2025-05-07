package pl.com.tenderflex.service.impl;

import static pl.com.tenderflex.model.enums.EOfferStatus.*;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.AwardDecision;
import pl.com.tenderflex.model.CompanyProfile;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.RejectDecision;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.model.enums.EOfferStatus;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.mapstract.OfferMapper;
import pl.com.tenderflex.payload.response.OfferCountResponse;
import pl.com.tenderflex.payload.response.OfferResponse;
import pl.com.tenderflex.payload.response.OfferStatusResponse;
import pl.com.tenderflex.repository.OfferRepository;
import pl.com.tenderflex.service.CompanyProfileService;
import pl.com.tenderflex.service.OfferService;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferMapper offerMapper;
    private final OfferRepository offerRepository;
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
    public OfferResponse findDetailsById(Integer offerId) {
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
    public void rejectUnsuitableOffers(Offer winningOffer, RejectDecision rejectDecision) {
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
    public Offer handleOfferOnContractDecline(Offer offer) {
        offer.setGlobalStatus(CONTRACT_DECLINED_BY_BIDDER);
        offerRepository.update(offer);
        return offer;
    }

}