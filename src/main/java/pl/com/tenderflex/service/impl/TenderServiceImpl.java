package pl.com.tenderflex.service.impl;

import static pl.com.tenderflex.model.enums.ELanguage.*;
import static pl.com.tenderflex.model.enums.EProcedure.*;
import static pl.com.tenderflex.model.enums.ETenderStatus.*;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.CompanyProfile;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.Procedure;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.model.enums.ETenderStatus;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.mapstract.TenderMapper;
import pl.com.tenderflex.payload.response.TenderCountResponse;
import pl.com.tenderflex.payload.response.TenderResponse;
import pl.com.tenderflex.repository.TenderRepository;
import pl.com.tenderflex.service.CompanyProfileService;
import pl.com.tenderflex.service.OfferService;
import pl.com.tenderflex.service.TenderService;

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
        tender.setProcedure(Procedure.builder().type(OPEN_PROCEDURE).language(ENGLISH).build());
        tender.setGlobalStatus(TENDER_IN_PROGRESS);
        tender = tenderRepository.save(tender);
        return tender;
    }

    @Override
    public Page<TenderResponse> findByContractorWithPagination(Integer userId, Integer currentPage,
            Integer tendersPerPage) {
        Integer countTendersToSkip = (currentPage - 1) * tendersPerPage;
        Integer allTendersCount = tenderRepository.countTendersByContractor(userId);
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
        Integer allTendersAmount = tenderRepository.countTenders();
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
    public TenderCountResponse countAll() {
        return new TenderCountResponse(tenderRepository.countTenders());
    }

    @Override
    public TenderCountResponse countByContractor(Integer userId) {
        return new TenderCountResponse(tenderRepository.countTendersByContractor(userId));
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