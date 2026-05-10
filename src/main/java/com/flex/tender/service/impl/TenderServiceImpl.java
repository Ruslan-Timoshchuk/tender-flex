package com.flex.tender.service.impl;

import static com.flex.tender.model.enumeration.ELanguage.*;
import static com.flex.tender.model.enumeration.EProcedure.*;
import static com.flex.tender.model.enumeration.ETenderStatus.*;
import static java.util.stream.Collectors.toMap;
import static com.flex.tender.model.enumeration.EOfferStatus.*;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.CompanyProfile;
import com.flex.tender.model.Offer;
import com.flex.tender.model.Procedure;
import com.flex.tender.model.Tender;
import com.flex.tender.model.enumeration.EOfferStatus;
import com.flex.tender.model.enumeration.ETenderStatus;
import com.flex.tender.payload.Page;
import com.flex.tender.payload.mapper.TenderMapper;
import com.flex.tender.payload.response.BidderTenderSummaryResponse;
import com.flex.tender.payload.response.ContractorTenderSummaryResponse;
import com.flex.tender.payload.response.TenderCountResponse;
import com.flex.tender.payload.response.TenderResponse;
import com.flex.tender.repository.TenderRepository;
import com.flex.tender.service.CompanyProfileService;
import com.flex.tender.service.OfferService;
import com.flex.tender.service.TenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenderServiceImpl implements TenderService {

    private final TenderMapper tenderMapper;
    private final TenderRepository tenderRepository;
    private final CompanyProfileService companyProfileService;
    private final OfferService offerService;

    @Override
    public Tender save(Tender tender) {
        CompanyProfile contractorProfile = companyProfileService.create(tender.getCompanyProfile());
        tender.setCompanyProfile(contractorProfile);
        tender.setProcedure(Procedure
                              .builder()
                              .type(OPEN_PROCEDURE)
                              .language(ENGLISH)
                              .build());
        tender.setGlobalStatus(TENDER_IN_PROGRESS);
        tender = tenderRepository.save(tender);
        return tender;
    }

    @Override
    public Page<ContractorTenderSummaryResponse> findByContractorWithPagination(Integer userId, Integer currentPage,
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
            var offersCounts = offerService.countOffersByTenderIds(tenderIds);
            contractorTendersPage = tendersPage
                    .stream()
                    .map(tender -> tenderMapper.toContractorTenderSummary(tender.getId(), tender.getCpv(),
                            tender.getCompanyProfile().getOfficialName(), tender.getGlobalStatus(),
                            tender.getOfferSubmissionDeadline(), offersCounts.get(tender.getId())))
                    .toList();
        }
        return new Page<>(currentPage, totalPages, contractorTendersPage);
    }

    @Override
    public Page<BidderTenderSummaryResponse> findByBidderWithPagination(Integer bidderId, Integer currentPage,
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
            var offersByTenderIds = offerService.findByBidderIdAndTenderIdIn(bidderId, tenderIds)
                    .stream()
                    .collect(toMap(offer -> offer.getTender().getId(), Function.identity()));
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
        return new Page<>(currentPage, totalPages, bidderTendersPage);
    }

    @Override
    public Tender findById(Integer id) {
        return tenderRepository.findById(id);
    }

    @Override
    public TenderResponse findDetailsById(Integer id) {
        Tender tender = tenderRepository.findById(id);
        return tenderMapper.toResponse(tender);
    }

    @Override
    public TenderCountResponse countByContractor(Integer contractorId) {
        return new TenderCountResponse(tenderRepository.countByContractor(contractorId));
    }

    @Override
    public TenderCountResponse countAll() {
        return new TenderCountResponse(tenderRepository.countAll());
    }

    @Override
    public Tender close(Tender tender) {
        tender.setGlobalStatus(TENDER_CLOSED);
        tenderRepository.update(tender);
        return tender;
    }

    @Override
    public Tender closeIfNoActiveOffers(Tender tender) {
        boolean hasActiveOffers = offerService
                .existsByTenderIdAndGlobalStatusIn(tender.getId(), List.of(SENT, SELECTED));
        if (hasActiveOffers) {
            tender.setGlobalStatus(TENDER_CLOSED);
            tenderRepository.update(tender);
        }
        return tender;
    }

    @Override
    @Transactional
    public void closeActiveWithExpiredSubmission(ETenderStatus status, LocalDate currentDate) {
        tenderRepository.findActiveWhereSubmissionIsExpired(status, currentDate)
                .forEach(this::closeIfNoActiveOffers);
    }  

}