package pl.com.tenderflex.service.impl;

import static pl.com.tenderflex.model.enums.ELanguage.*;
import static pl.com.tenderflex.model.enums.EProcedure.*;
import static pl.com.tenderflex.model.enums.ETenderStatus.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.AwardDecision;
import pl.com.tenderflex.model.CompanyProfile;
import pl.com.tenderflex.model.Contract;
import pl.com.tenderflex.model.Procedure;
import pl.com.tenderflex.model.RejectDecision;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.model.enums.ETenderStatus;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.mapstract.TenderMapper;
import pl.com.tenderflex.payload.request.TenderRequest;
import pl.com.tenderflex.payload.response.TenderCountResponse;
import pl.com.tenderflex.payload.response.TenderResponse;
import pl.com.tenderflex.repository.TenderRepository;
import pl.com.tenderflex.service.AwardDecisionService;
import pl.com.tenderflex.service.CompanyProfileService;
import pl.com.tenderflex.service.ContractService;
import pl.com.tenderflex.service.RejectDecisionService;
import pl.com.tenderflex.service.TenderService;
 
@Service
@RequiredArgsConstructor
public class TenderServiceImpl implements TenderService {

    private final TenderMapper tenderMapper;
    private final TenderRepository tenderRepository;
    private final CompanyProfileService companyProfileService;
    private final ContractService contractService;
    private final AwardDecisionService awardService;
    private final RejectDecisionService rejectService;

    @Override
    @Transactional
    public TenderResponse save(TenderRequest tenderRequest) {
        Tender tender = tenderMapper.toEntity(tenderRequest);       
        CompanyProfile contractorProfile = companyProfileService.create(tender.getCompanyProfile());   
        tender.setCompanyProfile(contractorProfile);
        tender.setProcedure(Procedure
                .builder()
                .type(OPEN_PROCEDURE)
                .language(ENGLISH)
                .build());
        tender.setGlobalStatus(TENDER_IN_PROGRESS);     
        tender = tenderRepository.save(tender);
        Contract contract = tender.getContract();
        contractService.save(contract);
        AwardDecision award = tender.getAwardDecision();
        awardService.save(award);
        RejectDecision reject = tender.getRejectDecision();  
        rejectService.save(reject);
        return tenderMapper.toResponse(tender, tender.getGlobalStatus());
    }

    @Override
    public Page<TenderResponse> findByContractorWithPagination(Integer userId, Integer currentPage, Integer tendersPerPage) {
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
                    ETenderStatus status = tender.getGlobalStatus();
                    return tenderMapper.toResponse(tender, status);
                }).toList());
    }

    @Override
    public Page<TenderResponse> findWithPagination(Integer currentPage, Integer tendersPerPage) {
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
                    ETenderStatus status = tender.getGlobalStatus();
                    return tenderMapper.toResponse(tender, status);
                }).toList());
    }

    @Override
    public TenderResponse findById(Integer tenderId) {
        Tender tender = tenderRepository.findById(tenderId);
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
    
}