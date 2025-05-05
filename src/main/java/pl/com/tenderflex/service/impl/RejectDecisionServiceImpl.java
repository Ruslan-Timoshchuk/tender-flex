package pl.com.tenderflex.service.impl;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.RejectDecision;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.repository.RejectDecisionRepository;
import pl.com.tenderflex.service.RejectDecisionService;

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