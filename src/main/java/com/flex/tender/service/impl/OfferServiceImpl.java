package com.flex.tender.service.impl;

import static com.flex.tender.model.enumeration.EOfferStatus.*;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.flex.tender.model.AwardDecision;
import com.flex.tender.model.CompanyProfile;
import com.flex.tender.model.Offer;
import com.flex.tender.model.RejectDecision;
import com.flex.tender.model.Tender;
import com.flex.tender.model.enumeration.EOfferStatus;
import com.flex.tender.payload.SummaryPage;
import com.flex.tender.payload.mapper.OfferMapper;
import com.flex.tender.payload.response.OfferCountResponse;
import com.flex.tender.payload.response.OfferDetailsResponse;
import com.flex.tender.payload.response.OfferSummaryResponse;
import com.flex.tender.repository.OfferRepository;
import com.flex.tender.service.CompanyProfileService;
import com.flex.tender.service.CpvService;
import com.flex.tender.service.OfferService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferMapper offerMapper;
    private final OfferRepository offerRepository;
    private final CompanyProfileService companyProfileService;
    private final CpvService cpvService;

    @Override
    public Offer save(Tender tender, Offer offer) {
        offer.setTender(tender);
        CompanyProfile companyProfile = companyProfileService.create(offer.getCompanyProfile());
        offer.setCompanyProfile(companyProfile);
        offer.setGlobalStatus(SENT);
        return offerRepository.save(offer);
    }

    @Override
    public SummaryPage<OfferSummaryResponse> findByBidderWithPagination(Integer bidderId, Integer page,
            Integer pageSize) {
        Integer offset = (page - 1) * pageSize;
        Integer totalOffers = offerRepository.countByBidder(bidderId);
        Integer totalPages = countTotalPages(pageSize, totalOffers);
        var offers = offerRepository.findByBidderWithPagination(bidderId, pageSize, offset);
        List<OfferSummaryResponse> offersPage = List.of();
        if(!offers.isEmpty()) {
            var offerIds = offers
                    .stream()
                    .map(Offer::getId)
                    .toList();
            var cpvs = cpvService.findByOfferIdIn(offerIds);
            offersPage = offers.stream()
                  .map(offer -> offerMapper.toBidderSummaryResponse(offer, cpvs.get(offer.getId())))
                  .toList();
        }
        return new SummaryPage<>(page, totalPages, offersPage);
    }

    @Override
    public SummaryPage<OfferSummaryResponse> findByContractorWithPagination(Integer contractorId, Integer page,
            Integer pageSize) {
        Integer offset = (page - 1) * pageSize;
        Integer totalOffers = offerRepository.countByContractor(contractorId);
        Integer totalPages = countTotalPages(pageSize, totalOffers);
        var offers = offerRepository.findByContractorWithPagination(contractorId, pageSize, offset);
        List<OfferSummaryResponse> offersPage = List.of();
        if(!offers.isEmpty()) {
            var offerIds = offers
                    .stream()
                    .map(Offer::getId)
                    .toList();
            var cpvs = cpvService.findByOfferIdIn(offerIds);
            offersPage = offers.stream()
                  .map(offer -> offerMapper.toContractorSummaryResponse(offer, cpvs.get(offer.getId())))
                  .toList();
        }
        return new SummaryPage<>(page, totalPages, offersPage);
    }

    @Override
    public SummaryPage<OfferSummaryResponse> findByTenderWithPagination(Integer tenderId, Integer page, Integer pageSize) {
        Integer offset = (page - 1) * pageSize;
        Integer totalOffers = offerRepository.countAllByTender(tenderId);
        Integer totalPages = countTotalPages(pageSize, totalOffers);
        var offers = offerRepository.findByTenderWithPagination(tenderId, pageSize, offset);
        List<OfferSummaryResponse> offersPage = List.of();
        if(!offers.isEmpty()) {
            var offerIds = offers
                    .stream()
                    .map(Offer::getId)
                    .toList();
            var cpvs = cpvService.findByOfferIdIn(offerIds);
            offersPage = offers.stream()
                  .map(offer -> offerMapper.toContractorSummaryResponse(offer, cpvs.get(offer.getId())))
                  .toList();
        }
        return new SummaryPage<>(page, totalPages, offersPage);
    }
    
    private Integer countTotalPages(Integer pageSize, Integer totalOffers) {
        return ((totalOffers + pageSize - 1) / pageSize);
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
    public OfferDetailsResponse findDetailsById(Integer offerId) {
        Offer offer = offerRepository.findById(offerId);
        return offerMapper.toResponse(offer);
    }
    
    @Override
    public OfferCountResponse countByBidder(Integer bidderId) {
        return new OfferCountResponse(offerRepository.countByBidder(bidderId));
    }

    @Override
    public OfferCountResponse countByContractor(Integer contractorId) {
        return new OfferCountResponse(offerRepository.countByContractor(contractorId));
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
        return offerRepository.countByTenderIdIn(tenderIds);
    }

}