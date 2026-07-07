package com.flex.tender.service.read.impl;

import static com.flex.tender.model.enumeration.EOfferStatus.*;
import static com.flex.tender.model.enumeration.ETenderStatus.*;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.AwardDecision;
import com.flex.tender.model.Contract;
import com.flex.tender.model.Offer;
import com.flex.tender.model.RejectDecision;
import com.flex.tender.model.Tender;
import com.flex.tender.model.enumeration.EOfferStatus;
import com.flex.tender.model.enumeration.ETenderStatus;
import com.flex.tender.payload.SummaryPage;
import com.flex.tender.payload.mapper.TenderMapper;
import com.flex.tender.payload.response.BidderTenderSummaryResponse;
import com.flex.tender.payload.response.ContractorTenderSummaryResponse;
import com.flex.tender.payload.response.TenderCountResponse;
import com.flex.tender.payload.response.TenderDetailsResponse;
import com.flex.tender.repository.TenderRepository;
import com.flex.tender.service.read.AwardDecisionDetailsService;
import com.flex.tender.service.read.ContractDetailsService;
import com.flex.tender.service.read.OfferDetailsBatchService;
import com.flex.tender.service.read.RejectDecisionDetailsService;
import com.flex.tender.service.read.TenderDetailsService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TenderDetailsServiceImpl implements TenderDetailsService {

    private final TenderMapper tenderMapper; 
    private final TenderRepository tenderRepository;
    private final ContractDetailsService contractDetailsService;
    private final AwardDecisionDetailsService awardDecisionDetailsService;
    private final RejectDecisionDetailsService rejectDecisionDetailsService;
    private final OfferDetailsBatchService offerDetailsBatchService;
   
    @Override
    public Tender findById(Integer id) {
        return tenderRepository.findById(id);
    }

    @Override
    public TenderDetailsResponse loadTenderDetailsById(Integer tenderId) {
        Tender tender = tenderRepository.findById(tenderId);
        AwardDecision awardDecision = awardDecisionDetailsService.findByTenderId(tenderId);
        Contract contract = contractDetailsService.findByAwardDecisionId(awardDecision.getId());     
        RejectDecision rejectDecision = rejectDecisionDetailsService.findByTenderId(tenderId);
        return tenderMapper.toContractorTenderDetailsResponse(tender, contract, awardDecision,
                rejectDecision);
    }

    @Override
    public SummaryPage<ContractorTenderSummaryResponse> findByContractorWithPagination(Integer userId, Integer currentPage,
            Integer tendersPerPage) {
        Integer countTendersToSkip = (currentPage - 1) * tendersPerPage;
        Integer allTendersCount = tenderRepository.countByContractor(userId);
        Integer totalPages = 1;
        if (allTendersCount >= tendersPerPage) {
            totalPages = allTendersCount / tendersPerPage;
            if (allTendersCount % tendersPerPage > 0) {
                totalPages++;
            }
        }
        var tendersPage = tenderRepository.findByContractorWithPagination(userId, tendersPerPage, countTendersToSkip);
        List<ContractorTenderSummaryResponse> contractorTendersPage = List.of();
        if (!tendersPage.isEmpty()) {
            var tenderIds = tendersPage
                    .stream()
                    .map(Tender::getId)
                    .toList();
            var offersCounts = offerDetailsBatchService.countOffersByTenderIds(tenderIds);
            contractorTendersPage = tendersPage
                    .stream()
                    .map(tender -> tenderMapper.toContractorTenderSummary(tender.getId(), tender.getCpv(),
                            tender.getCompanyProfile().getOfficialName(), tender.getGlobalStatus(),
                            tender.getOfferSubmissionDeadline(), offersCounts.getOrDefault(tender.getId(), 0)))
                    .toList();
        }
        return new SummaryPage<>(currentPage, totalPages, contractorTendersPage);
    }

    @Override
    public SummaryPage<BidderTenderSummaryResponse> findByBidderWithPagination(Integer bidderId, Integer currentPage,
            Integer tendersPerPage) {
        Integer amountTendersToSkip = (currentPage - 1) * tendersPerPage;
        Integer allTendersAmount = tenderRepository.countAll();
        Integer totalPages = 1;
        if (allTendersAmount >= tendersPerPage) {
            totalPages = allTendersAmount / tendersPerPage;
            if (allTendersAmount % tendersPerPage > 0) {
                totalPages++;
            }
        }
        var tendersPage = tenderRepository.findWithPagination(tendersPerPage, amountTendersToSkip);
        List<BidderTenderSummaryResponse> bidderTendersPage = List.of();
        if(!tendersPage.isEmpty()) {
            var tenderIds = tendersPage
                    .stream()
                    .map(Tender::getId)
                    .toList();
            var offersByTenderIds = offerDetailsBatchService.findByBidderIdAndTenderIdIn(bidderId, tenderIds);
            bidderTendersPage = tendersPage
                    .stream()
                    .map(tender -> { 
                        ETenderStatus tenderStatus = tender.getGlobalStatus();
                        EOfferStatus offerStatus = NOT_SENT;
                        Offer offer = offersByTenderIds.get(tender.getId());
                        if (offer != null) {
                             offerStatus = offer.getGlobalStatus();
                        if (tenderStatus.equals(TENDER_IN_PROGRESS)
                             && !(offerStatus.equals(SENT) || 
                                  offerStatus.equals(SELECTED))) {
                        tenderStatus = TENDER_CLOSED;
                    }
                }
                return tenderMapper.toBidderTenderSummary(tender.getId(), tender.getCpv(),
                        tender.getCompanyProfile().getOfficialName(), tenderStatus, tender.getOfferSubmissionDeadline(),
                        offerStatus);
            }).toList();
        }
        return new SummaryPage<>(currentPage, totalPages, bidderTendersPage);
    }

    @Override
    public TenderCountResponse countByContractor(Integer contractorId) {
        return new TenderCountResponse(tenderRepository.countByContractor(contractorId));
    }

    @Override
    public TenderCountResponse countAll() {
        return new TenderCountResponse(tenderRepository.countAll());
    }

}