package com.flex.tender.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.AwardDecision;
import com.flex.tender.repository.AwardDecisionRepository;
import com.flex.tender.service.AwardDecisionDetailsService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AwardDecisionDetailsServiceImpl implements AwardDecisionDetailsService {

    private final AwardDecisionRepository awardDecisionRepository;

    @Override
    public AwardDecision findById(Integer id) {
        return awardDecisionRepository.findById(id);
    }

    @Override
    public AwardDecision findByTenderId(Integer id) {
        return awardDecisionRepository.findByTenderId(id);
    }

}