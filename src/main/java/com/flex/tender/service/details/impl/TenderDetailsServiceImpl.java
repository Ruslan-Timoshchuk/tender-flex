package com.flex.tender.service.details.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.AwardDecision;
import com.flex.tender.model.Contract;
import com.flex.tender.model.RejectDecision;
import com.flex.tender.model.Tender;
import com.flex.tender.payload.mapper.TenderMapper;
import com.flex.tender.payload.response.BidderTenderDetailsResponse;
import com.flex.tender.payload.response.ContractorTenderDetailsResponse;
import com.flex.tender.repository.TenderRepository;
import com.flex.tender.service.details.AwardDecisionDetailsService;
import com.flex.tender.service.details.ContractDetailsService;
import com.flex.tender.service.details.RejectDecisionDetailsService;
import com.flex.tender.service.details.TenderDetailsService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TenderDetailsServiceImpl implements TenderDetailsService {

    private final TenderRepository tenderRepository;
    private final ContractDetailsService contractDetailsService;
    private final AwardDecisionDetailsService awardDecisionDetailsService;
    private final RejectDecisionDetailsService rejectDecisionDetailsService;
    private final TenderMapper tenderMapper;

    @Override
    public Tender findById(Integer id) {
        return tenderRepository.findById(id);
    }

    @Override
    public ContractorTenderDetailsResponse loadContractortTenderDetailsById(Integer tenderId) {
        Tender tender = tenderRepository.findById(tenderId);
        Contract contract = contractDetailsService.findByTenderId(tenderId);
        AwardDecision awardDecision = awardDecisionDetailsService.findByTenderId(tenderId);
        RejectDecision rejectDecision = rejectDecisionDetailsService.findByTenderId(tenderId);
        return tenderMapper.toContractorTenderDetailsResponse(tender, contract, awardDecision,
                rejectDecision);
    }

    @Override
    public BidderTenderDetailsResponse loadBidderTenderDetailsById(Integer tenderId) {
        Tender tender = tenderRepository.findById(tenderId);
        Contract contract = contractDetailsService.findByTenderId(tenderId);
        return tenderMapper.toBidderTenderDetailsResponse(tender, contract);
    }

}