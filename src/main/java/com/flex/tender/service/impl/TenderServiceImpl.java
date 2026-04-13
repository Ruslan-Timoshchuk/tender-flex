package com.flex.tender.service.impl;

import static com.flex.tender.model.enumeration.ELanguage.*;
import static com.flex.tender.model.enumeration.EProcedure.*;
import static com.flex.tender.model.enumeration.ETenderStatus.*;
import static java.util.stream.Collectors.toMap;
import static com.flex.tender.model.enumeration.EOfferStatus.*;
import org.springframework.security.access.AccessDeniedException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.CompanyProfile;
import com.flex.tender.model.Offer;
import com.flex.tender.model.Procedure;
import com.flex.tender.model.Tender;
import com.flex.tender.model.enumeration.EAuthority;
import com.flex.tender.model.enumeration.EOfferStatus;
import com.flex.tender.model.enumeration.ETenderStatus;
import com.flex.tender.payload.Page;
import com.flex.tender.payload.mapper.TenderMapper;
import com.flex.tender.payload.response.BidderTenderSummaryResponse;
import com.flex.tender.payload.response.ContractorTenderSummaryResponse;
import com.flex.tender.payload.response.TenderCountResponse;
import com.flex.tender.payload.response.TenderResponse;
import com.flex.tender.repository.TenderRepository;
import com.flex.tender.service.AuthorityService;
import com.flex.tender.service.CompanyProfileService;
import com.flex.tender.service.OfferService;
import com.flex.tender.service.TenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenderServiceImpl implements TenderService {

    public static final String LOG_MSG_ON_COUNT_TENDERS_FORBIDDEN = 
            "Failed to count tenders for userId = {}, authorities = {}: missing required authority";

    private final TenderMapper tenderMapper;
    private final TenderRepository tenderRepository;
    private final AuthorityService authorityService;
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
        var tenderIds = tendersPage
                .stream()
                .map(Tender::getId)
                .toList();
        var offersCounts = offerService.countOffersByTenderIds(tenderIds);
        var contractorTendersPage = tendersPage
                .stream()
                .map(tender -> tenderMapper
                        .toContractorTenderSummary(tender.getId(), tender.getCpv(),
                            tender.getCompanyProfile().getOfficialName(), tender.getGlobalStatus(), 
                            tender.getOfferSubmissionDeadline(), offersCounts.get(tender.getId()))).toList();
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
        var tenderIds = tendersPage
                .stream()
                .map(Tender::getId)
                .toList();
        var offersByTenderIds = offerService.findByBidderIdAndTenderIdIn(bidderId, tenderIds)
                .stream()
                .collect(toMap(offer -> offer.getTender().getId(), Function.identity()));
        var bidderTendersPage = tendersPage
                .stream()
                .map(tender -> {
            ETenderStatus tenderStatus = tender.getGlobalStatus();
            EOfferStatus offerStatus = NOT_SENT;
            Offer offer = offersByTenderIds.get(tender.getId());
            if (offer != null) {
                offerStatus = offer.getGlobalStatus();
                if (tenderStatus.equals(TENDER_IN_PROGRESS)
                        && !(offerStatus.equals(SENT) || offerStatus.equals(SELECTED))) {
                    tenderStatus = TENDER_CLOSED;
                }
            }
            return tenderMapper.toBidderTenderSummary(tender.getId(), tender.getCpv(),
                    tender.getCompanyProfile().getOfficialName(), tenderStatus, tender.getOfferSubmissionDeadline(),
                    offerStatus);
        }).toList();
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
    public TenderCountResponse countByUserAuthority(Integer userId, Collection<? extends GrantedAuthority> authorities) {
        if(authorityService.hasAuthority(authorities, EAuthority.CONTRACTOR)) {
            return new TenderCountResponse(tenderRepository.countByContractor(userId)); 
        } else if (authorityService.hasAuthority(authorities, EAuthority.BIDDER)) {
            return new TenderCountResponse(tenderRepository.countAll());
        } else {
            log.warn(LOG_MSG_ON_COUNT_TENDERS_FORBIDDEN, userId, authorities);
            throw new AccessDeniedException(
                    "User does not have the required authority to count tenders"
                );
        }
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