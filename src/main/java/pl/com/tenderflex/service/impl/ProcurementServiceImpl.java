package pl.com.tenderflex.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.AwardDecision;
import pl.com.tenderflex.model.Contract;
import pl.com.tenderflex.model.RejectDecision;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.payload.mapstract.AwardDecisionMapper;
import pl.com.tenderflex.payload.mapstract.ContractMapper;
import pl.com.tenderflex.payload.mapstract.RejectDecisionMapper;
import pl.com.tenderflex.payload.mapstract.TenderMapper;
import pl.com.tenderflex.payload.request.ProcurementRequest;
import pl.com.tenderflex.payload.response.ProcurementResponse;
import pl.com.tenderflex.service.AwardDecisionService;
import pl.com.tenderflex.service.ContractService;
import pl.com.tenderflex.service.ProcurementService;
import pl.com.tenderflex.service.RejectDecisionService;
import pl.com.tenderflex.service.TenderService;

@Service
@RequiredArgsConstructor
public class ProcurementServiceImpl implements ProcurementService {

    private final TenderService tenderService;
    private final TenderMapper tenderMapper;
    private final ContractService contractService;
    private final ContractMapper contractMapper;
    private final AwardDecisionService awardDecisionService;
    private final AwardDecisionMapper awardDecisionMapper;
    private final RejectDecisionService rejectDecisionService;
    private final RejectDecisionMapper rejectDecisionMapper;

    @Override
    @Transactional
    public ProcurementResponse initiateProcurement(ProcurementRequest procurementRequest) {
        Tender tender = tenderService.save(tenderMapper.toEntity(procurementRequest.tender()));
        Contract contract = contractService.save(contractMapper.toEntity(procurementRequest.contract()), tender);
        AwardDecision awardDecision = awardDecisionService
                .save(awardDecisionMapper.toEntity(procurementRequest.awardDecision()), tender);
        RejectDecision rejectDecision = rejectDecisionService
                .save(rejectDecisionMapper.toEntity(procurementRequest.rejectDecision()), tender);
        return new ProcurementResponse(tender.getId(), contract.getId(), awardDecision.getId(), rejectDecision.getId());
    }

}