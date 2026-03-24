package com.flex.tender.service.impl;

import org.springframework.stereotype.Service;

import com.flex.tender.model.RejectDecision;
import com.flex.tender.model.Tender;
import com.flex.tender.repository.RejectDecisionRepository;
import com.flex.tender.service.RejectDecisionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RejectDecisionServiceImpl implements RejectDecisionService {

    private final RejectDecisionRepository rejectRepository;

    @Override
    public RejectDecision save(RejectDecision rejectDecision, Tender tender) {
        rejectDecision.setTender(tender);
        return rejectRepository.save(rejectDecision);
    }

    @Override
    public RejectDecision findById(Integer id) {
        return rejectRepository.findById(id);
    }

}