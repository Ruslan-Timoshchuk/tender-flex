package com.flex.tender.service.write.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.RejectDecision;
import com.flex.tender.payload.mapper.RejectDecisionMapper;
import com.flex.tender.payload.request.RejectDecisionRequest;
import com.flex.tender.repository.RejectDecisionRepository;
import com.flex.tender.service.read.FileStorageDetailsService;
import com.flex.tender.service.write.RejectDecisionService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RejectDecisionServiceImpl implements RejectDecisionService {

    private final RejectDecisionMapper rejectDecisionMapper;
    private final FileStorageDetailsService fileStorageDetailsService;
    private final RejectDecisionRepository rejectDecisionRepository;

    @Override
    public RejectDecision buildEntity(RejectDecisionRequest rejectDecisionRequest) {
        RejectDecision rejectDecision = rejectDecisionMapper.toEntity(rejectDecisionRequest);
        rejectDecision.setFileMetadata(fileStorageDetailsService.findById(rejectDecisionRequest.fileMetadataId()));
        return rejectDecision;
    }
    
    @Override
    public RejectDecision save(RejectDecision rejectDecision) {
        return rejectDecisionRepository.save(rejectDecision);
    }

}