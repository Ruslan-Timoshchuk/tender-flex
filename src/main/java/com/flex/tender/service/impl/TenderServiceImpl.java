package com.flex.tender.service.impl;

import static com.flex.tender.model.enumeration.ELanguage.*;
import static com.flex.tender.model.enumeration.EProcedure.*;
import static com.flex.tender.model.enumeration.ETenderStatus.*;
import org.springframework.security.access.AccessDeniedException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.CompanyProfile;
import com.flex.tender.model.Offer;
import com.flex.tender.model.Procedure;
import com.flex.tender.model.Tender;
import com.flex.tender.model.enumeration.EAuthority;
import com.flex.tender.model.enumeration.ETenderStatus;
import com.flex.tender.payload.Page;
import com.flex.tender.payload.mapstract.TenderMapper;
import com.flex.tender.payload.response.TenderCountResponse;
import com.flex.tender.payload.response.TenderResponse;
import com.flex.tender.repository.TenderRepository;
import com.flex.tender.service.AuthorityService;
import com.flex.tender.service.CompanyProfileService;
import com.flex.tender.service.OfferService;
import com.flex.tender.service.TenderService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TenderServiceImpl implements TenderService {

    private final TenderMapper tenderMapper;
    private final TenderRepository tenderRepository;
    private final AuthorityService authorityService;
    private final CompanyProfileService companyProfileService;
    private final OfferService offerService;

    @Override
    public Tender save(Tender tender) {
        CompanyProfile contractorProfile = companyProfileService.create(tender.getCompanyProfile());
        tender.setCompanyProfile(contractorProfile);
        tender.setProcedure(Procedure.builder().type(OPEN_PROCEDURE).language(ENGLISH).build());
        tender.setGlobalStatus(TENDER_IN_PROGRESS);
        tender = tenderRepository.save(tender);
        return tender;
    }

    @Override
    public Page<TenderResponse> findByContractorWithPagination(Integer userId, Integer currentPage,
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
        return new Page<>(currentPage, totalPages, tenderRepository
                .findByContractorWithPagination(userId, tendersPerPage, countTendersToSkip).stream().map(tender -> {
                    ETenderStatus tenderStatus = tender.getGlobalStatus();
                    return tenderMapper.toResponse(tender, tenderStatus);
                }).toList());
    }

    @Override
    public Page<TenderResponse> findByBidderWithPagination(Integer userId, Integer currentPage,
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
        return new Page<>(currentPage, totalPages,
                tenderRepository.findWithPagination(tendersPerPage, amountTendersToSkip).stream().map(tender -> {
                    ETenderStatus tenderStatus = tender.getGlobalStatus();
                    Optional<Offer> foundlOffer = offerService.findOfferByTenderAndBidder(tender.getId(), userId);
                    if (tenderStatus.equals(TENDER_IN_PROGRESS) && foundlOffer.isPresent()) {
                        Offer offer = foundlOffer.get();
                        if (!offerService.hasContract(offer) && offerService.hasAwardDecision(offer)
                                || offerService.hasRejectDecision(offer)) {
                            tenderStatus = TENDER_CLOSED;
                        }
                    }
                    return tenderMapper.toResponse(tender, tenderStatus);
                }).toList());
    }

    @Override
    public Tender findById(Integer id) {
        return tenderRepository.findById(id);
    }

    @Override
    public TenderResponse findDetailsById(Integer id) {
        Tender tender = tenderRepository.findById(id);
        ETenderStatus status = tender.getGlobalStatus();
        return tenderMapper.toResponse(tender, status);
    }

    @Override
    public TenderCountResponse countByUserAuthority(Integer userId, Collection<? extends GrantedAuthority> authorities) {
        if(authorityService.hasAuthority(authorities, EAuthority.CONTRACTOR)) {
            return new TenderCountResponse(tenderRepository.countByContractor(userId)); 
        } else if (authorityService.hasAuthority(authorities, EAuthority.BIDDER)) {
            return new TenderCountResponse(tenderRepository.countAll());
        } else {
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
    public Tender closeIfHasNoPendingOffers(Tender tender) {
        boolean hasNoPendingOffers = offerService.findAllByTender(tender.getId()).stream()
                .filter(offer -> !offerService.hasAwardDecision(offer) && !offerService.hasRejectDecision(offer))
                .toList().isEmpty();
        if (hasNoPendingOffers) {
            tender.setGlobalStatus(TENDER_CLOSED);
            tenderRepository.update(tender);
        }
        return tender;
    }

    @Override
    @Transactional
    public void closeActiveWithExpiredSubmission(ETenderStatus status, LocalDate currentDate) {
        tenderRepository.findActiveWhereSubmissionIsExpired(status, currentDate)
                .forEach(this::closeIfHasNoPendingOffers);
    }

}