package com.flex.tender.service.impl;

import static com.flex.tender.model.enumeration.EOfferStatus.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
import com.flex.tender.payload.mapper.OfferMapper;
import com.flex.tender.payload.response.OfferCountResponse;
import com.flex.tender.payload.response.OfferResponse;
import com.flex.tender.payload.response.OfferSummaryResponse;
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
        offer.setGlobalStatus(SENT);
        return offerRepository.save(offer);
    }

    @Override
    public Page<OfferSummaryResponse> findByBidderWithPagination(Integer bidderId, Integer currentPage, Integer offersPerPage) {
        Integer amountOffersToSkip = (currentPage - 1) * offersPerPage;
        Integer allOffersAmount = offerRepository.countAllByBidder(bidderId);
        Integer totalPages = 1;
        if (allOffersAmount >= offersPerPage) {
            totalPages = allOffersAmount / offersPerPage;
            if (allOffersAmount % offersPerPage > 0) {
                totalPages++;
            }
        }
        return new Page<>(currentPage, totalPages,
                offerRepository.findByBidderWithPagination(bidderId, offersPerPage, amountOffersToSkip).stream()
                        .map(offerMapper::toBidderSummaryResponse)
                        .toList());
    }

    @Override
    public Page<OfferSummaryResponse> findByContractorWithPagination(Integer contractorId, Integer currentPage,
            Integer offersPerPage) {
        Integer amountOffersToSkip = (currentPage - 1) * offersPerPage;
        Integer allOffersAmount = offerRepository.countAllByContractor(contractorId);
        Integer totalPages = 1;
        if (allOffersAmount >= offersPerPage) {
            totalPages = allOffersAmount / offersPerPage;
            if (allOffersAmount % offersPerPage > 0) {
                totalPages++;
            }
        }
        return new Page<>(currentPage, totalPages,
                offerRepository.findByContractorWithPagination(contractorId, offersPerPage, amountOffersToSkip).stream()
                        .map(offerMapper::toContractorSummaryResponse).toList());
    }

    @Override
    public Page<OfferSummaryResponse> findByTenderWithPagination(Integer tenderId, Integer currentPage, Integer offersPerPage) {
        Integer amountOffersToSkip = (currentPage - 1) * offersPerPage;
        Integer allOffersAmount = offerRepository.countAllByTender(tenderId);
        Integer totalPages = 1;
        if (allOffersAmount >= offersPerPage) {
            totalPages = allOffersAmount / offersPerPage;
            if (allOffersAmount % offersPerPage > 0) {
                totalPages++;
            }
        }
        return new Page<>(currentPage, totalPages, offerRepository
                .findByTenderWithPagination(tenderId, offersPerPage, amountOffersToSkip)
                .stream().map(offerMapper::toContractorSummaryResponse).toList());
    }

    @Override
    public boolean existsByTenderIdAndGlobalStatusIn(Integer tenderId, List<EOfferStatus> statuses) {
        return offerRepository.existsByTenderIdAndGlobalStatusIn(tenderId, statuses);
    }

    @Override
    public Offer findById(Integer offerId) {
        return offerRepository.findById(offerId);
    }

    @Override
    public OfferResponse findDetailsByBidder(Integer offerId) {
        Offer offer = offerRepository.findById(offerId);
        return offerMapper.toBidderResponse(offer);
    }
    
    @Override
    public OfferResponse findDetailsByContractor(Integer offerId) {
        Offer offer = offerRepository.findById(offerId);  
        return offerMapper.toContractorResponse(offer);
    }

    @Override
    public OfferCountResponse countByUserAuthority(Integer userId, Collection<? extends GrantedAuthority> authorities) {
        if (authorityService.hasAuthority(authorities, EAuthority.CONTRACTOR)) {
            return new OfferCountResponse(offerRepository.countAllByContractor(userId));
        } else if (authorityService.hasAuthority(authorities, EAuthority.BIDDER)) {
            return new OfferCountResponse(offerRepository.countAllByBidder(userId));
        } else {
            throw new AccessDeniedException("User does not have the required authority to count offers");
        }
    }

    @Override
    public List<Offer> findByBidderIdAndTenderIdIn(Integer userId, List<Integer> tenderIds) {
        return offerRepository.findByBidderIdAndTenderIdIn(userId, tenderIds);
    }

    @Override
    public Offer selectWinningOffer(Offer offer, AwardDecision awardDecision) {
        offer.setAwardDecision(awardDecision);
        offer.setGlobalStatus(SELECTED);
        offerRepository.update(offer);
        return offer;
    }

    @Override
    public Offer rejectOffer(Offer offer, RejectDecision rejectDecision) {
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
    public boolean hasContract(Offer offer) {
        return offer.getContract() != null && offer.getContract().getId() != null;
    }

    @Override
    public Offer handleOnContractDecline(Offer offer) {
        offer.setGlobalStatus(CONTRACT_DECLINED);
        offerRepository.update(offer);
        return offer;
    }
    
    @Override
    public Offer handleOnSigningDeadlinePassed(Offer offer) {
        offer.setGlobalStatus(REJECTED_BY_BIDDER);
        offerRepository.update(offer);
        return offer;
    }

    @Override
    public Map<Integer, Integer> countOffersByTenderIds(List<Integer> tenderIds) {
        return offerRepository.countOffersByTenderIds(tenderIds);
    }

}