package pl.com.tenderflex.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.AwardDecision;
import pl.com.tenderflex.repository.AwardDecisionRepository;
import pl.com.tenderflex.service.AwardDecisionService;

@Service
@RequiredArgsConstructor
public class AwardDecisionServiceImpl implements AwardDecisionService {

    private final AwardDecisionRepository awardDecisionRepository;

    @Override
    @Transactional
    public AwardDecision create(AwardDecision awardDecision) {
        return awardDecisionRepository.save(awardDecision);
    }

}