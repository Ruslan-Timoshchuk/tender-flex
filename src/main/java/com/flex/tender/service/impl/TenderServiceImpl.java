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
import com.flex.tender.model.Cpv;
import com.flex.tender.model.Offer;
import com.flex.tender.model.Procedure;
import com.flex.tender.model.Tender;
import com.flex.tender.model.embedded.PrincipalSummary;
import com.flex.tender.model.enumeration.EOfferStatus;
import com.flex.tender.model.enumeration.ETenderStatus;
import com.flex.tender.payload.SummaryPage;
import com.flex.tender.payload.mapper.TenderMapper;
import com.flex.tender.payload.request.TenderRequest;
import com.flex.tender.payload.response.BidderTenderSummaryResponse;
import com.flex.tender.payload.response.ContractorTenderSummaryResponse;
import com.flex.tender.payload.response.TenderCountResponse;
import com.flex.tender.payload.response.TenderResponse;
import com.flex.tender.repository.TenderRepository;
import com.flex.tender.service.CompanyProfileService;
import com.flex.tender.service.CpvService;
import com.flex.tender.service.OfferService;
import com.flex.tender.service.TenderService;
import com.flex.tender.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenderServiceImpl implements TenderService {

    private final TenderMapper tenderMapper;
    private final UserService userService;
    private final CompanyProfileService companyProfileService;
    private final CpvService cpvService;
    private final TenderRepository tenderRepository;
    private final OfferService offerService;

    @Override
    @Transactional
    public ContractorTenderSummaryResponse save(PrincipalSummary principalSummary, TenderRequest tenderRequest) {
        Tender tender = tenderMapper.toEntity(tenderRequest);
        tender.setContractor(userService.findById(principalSummary.userId()));
        CompanyProfile contractorProfile = companyProfileService.save(tenderRequest.companyProfile());
        tender.setCompanyProfile(contractorProfile);
        Cpv cpv = cpvService.findById(tenderRequest.cpvId());
        tender.setCpv(cpv);
        tender.setProcedure(Procedure
                              .builder()
                              .type(OPEN_PROCEDURE)
                              .language(ENGLISH)
                              .build());
        ETenderStatus status = TENDER_IN_PROGRESS;
        tender.setGlobalStatus(status);
        tender = tenderRepository.save(tender);
        Integer offers = 0;
        return tenderMapper.toContractorTenderSummary(tender.getId(), cpv, contractorProfile.getOfficialName(),
                status, tender.getOfferSubmissionDeadline(), offers);
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
            var offersCounts = offerService.countOffersByTenderIds(tenderIds);
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
        return new SummaryPage<>(currentPage, totalPages, bidderTendersPage);
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