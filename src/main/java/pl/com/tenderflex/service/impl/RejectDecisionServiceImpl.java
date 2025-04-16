package pl.com.tenderflex.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.RejectDecision;
import pl.com.tenderflex.repository.RejectDecisionRepository;
import pl.com.tenderflex.service.RejectDecisionService;

@Service
@RequiredArgsConstructor
public class RejectDecisionServiceImpl implements RejectDecisionService {

    private final RejectDecisionRepository rejectRepository;

    @Override
    @Transactional
    public RejectDecision save(RejectDecision rejectDecision) {
        return rejectRepository.save(rejectDecision);
    }

}