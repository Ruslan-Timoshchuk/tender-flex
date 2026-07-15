package com.flex.tender.service.write.impl;

import static com.flex.tender.model.enumeration.ELanguage.*;
import static com.flex.tender.model.enumeration.EProcedure.*;
import static com.flex.tender.model.enumeration.ETenderStatus.*;
import static com.flex.tender.model.enumeration.EOfferStatus.*;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.Cpv;
import com.flex.tender.model.Tender;
import com.flex.tender.model.embedded.PrincipalSummary;
import com.flex.tender.model.embedded.Procedure;
import com.flex.tender.model.enumeration.ETenderStatus;
import com.flex.tender.payload.mapper.TenderMapper;
import com.flex.tender.payload.request.TenderRequest;
import com.flex.tender.repository.TenderRepository;
import com.flex.tender.service.read.CpvDetailsService;
import com.flex.tender.service.read.CustomUserDetailsService;
import com.flex.tender.service.read.OfferDetailsBatchService;
import com.flex.tender.service.write.TenderService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TenderServiceImpl implements TenderService {

    private final TenderMapper tenderMapper;
    private final CustomUserDetailsService userService;
    private final CpvDetailsService cpvService;
    private final TenderRepository tenderRepository;
    private final OfferDetailsBatchService offerDetailsBatchService;

    @Override
    public Tender buildEntity(PrincipalSummary principalSummary, TenderRequest tenderRequest) {
        Tender tender = tenderMapper.toEntity(tenderRequest);
        tender.setContractor(userService.findById(principalSummary.userId()));
        Cpv cpv = cpvService.findById(tenderRequest.cpvId());
        tender.setCpv(cpv);
        tender.setProcedure(Procedure
                              .builder()
                              .type(OPEN_PROCEDURE)
                              .language(ENGLISH)
                              .build());
        ETenderStatus status = TENDER_IN_PROGRESS;
        tender.setGlobalStatus(status);
        return tender;
    }
    
    @Override
    public Tender save(Tender tender) {
        return tenderRepository.save(tender);
    }
    
    @Override
    public Tender close(Tender tender) {
        tender.setGlobalStatus(TENDER_CLOSED);
        tenderRepository.update(tender);
        return tender;
    }

    @Override
    public Tender closeIfNoActiveOffers(Tender tender) {
        boolean hasActiveOffers = offerDetailsBatchService
                .existsByTenderIdAndGlobalStatusIn(tender.getId(), List.of(SENT, SELECTED));
        if (hasActiveOffers) {
            tender.setGlobalStatus(TENDER_CLOSED);
            tenderRepository.update(tender);
        }
        return tender;
    }

    @Override
    public void closeActiveWithExpiredSubmission(ETenderStatus status, LocalDate currentDate) {
        tenderRepository.findActiveWhereSubmissionIsExpired(status, currentDate)
                .forEach(this::closeIfNoActiveOffers);
    }

    @Override
    public void handleOnContractApprove(Tender tender) {
        tender.setGlobalStatus(TENDER_CLOSED);
        tenderRepository.update(tender);
    }  

}