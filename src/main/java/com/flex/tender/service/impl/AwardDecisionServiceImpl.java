package com.flex.tender.service.impl;

import org.springframework.stereotype.Service;

import com.flex.tender.model.AwardDecision;
import com.flex.tender.model.Tender;
import com.flex.tender.repository.AwardDecisionRepository;
import com.flex.tender.service.AwardDecisionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AwardDecisionServiceImpl implements AwardDecisionService {

    private final AwardDecisionRepository awardRepository;

    @Override
    public AwardDecision save(AwardDecision awardDecision, Tender tender) {
        awardDecision.setTender(tender);
        return awardRepository.save(awardDecision);
    }

    @Override
    public AwardDecision findById(Integer id) {
        return awardRepository.findById(id);
    }

}