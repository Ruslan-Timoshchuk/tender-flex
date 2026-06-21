package com.flex.tender.service.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.AwardDecision;
import com.flex.tender.model.CompanyProfile;
import com.flex.tender.model.Contract;
import com.flex.tender.model.RejectDecision;
import com.flex.tender.model.Tender;
import com.flex.tender.model.embedded.PrincipalSummary;
import com.flex.tender.payload.mapper.TenderMapper;
import com.flex.tender.payload.request.TenderRequest;
import com.flex.tender.payload.response.ContractorTenderSummaryResponse;
import com.flex.tender.service.facade.TenderManager;
import com.flex.tender.service.transactional.AwardDecisionService;
import com.flex.tender.service.transactional.CompanyProfileService;
import com.flex.tender.service.transactional.ContractService;
import com.flex.tender.service.transactional.RejectDecisionService;
import com.flex.tender.service.transactional.TenderService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TenderManagerImpl implements TenderManager {

    private final TenderMapper tenderMapper;
    
    private final TenderService tenderService;
    private final CompanyProfileService companyProfileService;
    private final AwardDecisionService awardDecisionService;
    private final ContractService contractService;
    private final RejectDecisionService rejectDecisionService; 
    
    
    @Override
    public ContractorTenderSummaryResponse save(PrincipalSummary principalSummary, TenderRequest tenderRequest) {
        Tender tender = tenderService.buildEntity(principalSummary, tenderRequest);
        CompanyProfile contractorProfile = companyProfileService.buildEntity(tenderRequest.companyProfile());
        companyProfileService.save(contractorProfile);
        tender.setCompanyProfile(contractorProfile);
        tender = tenderService.save(tender);
        
        RejectDecision rejectDecision = rejectDecisionService.buildEntity(tenderRequest.rejectDecision());
        rejectDecision.setTender(tender);
        rejectDecisionService.save(rejectDecision);
        
        AwardDecision awardDecision = awardDecisionService.buildEntity(tenderRequest.awardDecision());
        awardDecision.setTender(tender);
        awardDecision = awardDecisionService.save(awardDecision);
        
        Contract contract = contractService.buildEntity(tenderRequest.contract());   
        contract.setAwardDecision(awardDecision);
        contractService.save(contract);
        
        Integer offers = 0;
        return tenderMapper.toContractorTenderSummary(tender.getId(), tender.getCpv(), contractorProfile.getOfficialName(),
                tender.getGlobalStatus(), tender.getOfferSubmissionDeadline(), offers);
    }
    
}