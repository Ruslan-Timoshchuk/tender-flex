package pl.com.tenderflex.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.RejectRepository;
import pl.com.tenderflex.payload.iresponse.response.RejectResponse;
import pl.com.tenderflex.payload.mapstract.RejectMapper;
import pl.com.tenderflex.payload.request.RejectRequest;
import pl.com.tenderflex.service.RejectService;

@Service
@RequiredArgsConstructor
public class RejectServiceImpl implements RejectService {

    private final RejectMapper rejectMapper;
    private final RejectRepository rejectRepository;

    @Override
    @Transactional
    public RejectResponse create(RejectRequest rejectRequest) {
        return rejectMapper.rejectDecisionToRejectResponse(
                rejectRepository.save(rejectMapper.rejectRequestToRejectDecision(rejectRequest)));
    }

}