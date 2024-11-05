package pl.com.tenderflex.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.AwardRepository;
import pl.com.tenderflex.payload.iresponse.response.AwardResponse;
import pl.com.tenderflex.payload.mapstract.AwardMapper;
import pl.com.tenderflex.payload.request.AwardRequest;
import pl.com.tenderflex.service.AwardService;

@Service
@RequiredArgsConstructor
public class AwardServiceImpl implements AwardService {

    private final AwardMapper awardMapper;
    private final AwardRepository awardRepository;

    @Override
    @Transactional
    public AwardResponse create(AwardRequest awardRequest) {
        return awardMapper.awardDecisionToAwardResponse(
                awardRepository.save(awardMapper.awardRequestToAwardDecision(awardRequest)));
    }

}