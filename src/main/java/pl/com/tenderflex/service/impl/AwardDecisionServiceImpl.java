package pl.com.tenderflex.service.impl;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.AwardDecision;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.repository.AwardDecisionRepository;
import pl.com.tenderflex.service.AwardDecisionService;

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