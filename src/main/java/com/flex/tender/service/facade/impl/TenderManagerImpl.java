package com.flex.tender.service.facade.impl;

import static com.flex.tender.model.enumeration.ELanguage.ENGLISH;
import static com.flex.tender.model.enumeration.EProcedure.OPEN_PROCEDURE;
import static com.flex.tender.model.enumeration.ETenderStatus.TENDER_IN_PROGRESS;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.AwardDecision;
import com.flex.tender.model.CompanyProfile;
import com.flex.tender.model.Contract;
import com.flex.tender.model.Cpv;
import com.flex.tender.model.RejectDecision;
import com.flex.tender.model.Tender;
import com.flex.tender.model.embedded.PrincipalSummary;
import com.flex.tender.model.embedded.Procedure;
import com.flex.tender.model.enumeration.ETenderStatus;
import com.flex.tender.payload.mapper.TenderMapper;
import com.flex.tender.payload.request.TenderRequest;
import com.flex.tender.payload.response.ContractorTenderSummaryResponse;
import com.flex.tender.repository.TenderRepository;
import com.flex.tender.service.UserService;
import com.flex.tender.service.details.CpvService;
import com.flex.tender.service.facade.TenderManager;
import com.flex.tender.service.transactional.AwardDecisionService;
import com.flex.tender.service.transactional.CompanyProfileService;
import com.flex.tender.service.transactional.ContractService;
import com.flex.tender.service.transactional.RejectDecisionService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TenderManagerImpl implements TenderManager {

    private final TenderMapper tenderMapper;
    private final TenderRepository tenderRepository;
    private final UserService userService;
    private final CompanyProfileService companyProfileService;
    private final CpvService cpvService;
    private final AwardDecisionService awardDecisionService;
    private final ContractService contractService;
    private final RejectDecisionService rejectDecisionService;
    
    @Override
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
        AwardDecision awardDecision = awardDecisionService.buildEntity(tenderRequest.awardDecision());
        awardDecision.setTender(tender);
        awardDecision = awardDecisionService.save(awardDecision);
        Contract contract = contractService.buildEntity(tenderRequest.contract());   
        contract.setAwardDecision(awardDecision);
        contractService.save(contract);
        RejectDecision rejectDecision = rejectDecisionService.buildEntity(tenderRequest.rejectDecision());
        rejectDecision.setTender(tender);
        rejectDecisionService.save(rejectDecision);
        Integer offers = 0;
        return tenderMapper.toContractorTenderSummary(tender.getId(), cpv, contractorProfile.getOfficialName(),
                status, tender.getOfferSubmissionDeadline(), offers);
    }
    
}