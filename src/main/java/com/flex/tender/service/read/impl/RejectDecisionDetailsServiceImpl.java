package com.flex.tender.service.read.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.RejectDecision;
import com.flex.tender.repository.RejectDecisionRepository;
import com.flex.tender.service.read.RejectDecisionDetailsService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RejectDecisionDetailsServiceImpl implements RejectDecisionDetailsService {

    private final RejectDecisionRepository rejectDecisionRepository;

    @Override
    public RejectDecision findById(Integer id) {
        return rejectDecisionRepository.findById(id);
    }

    @Override
    public RejectDecision findByTenderId(Integer id) {
        return rejectDecisionRepository.findByTenderId(id);
    }

}