package com.flex.tender.service.transactional.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.RejectDecision;
import com.flex.tender.payload.mapper.RejectDecisionMapper;
import com.flex.tender.payload.request.RejectDecisionRequest;
import com.flex.tender.payload.response.RejectDecisionResponse;
import com.flex.tender.repository.RejectDecisionRepository;
import com.flex.tender.service.FileStorageService;
import com.flex.tender.service.TenderDetailsService;
import com.flex.tender.service.transactional.RejectDecisionService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RejectDecisionServiceImpl implements RejectDecisionService {

    private final RejectDecisionMapper rejectDecisionMapper;
    private final TenderDetailsService tenderDetailsService;
    private final FileStorageService fileStorageService;
    private final RejectDecisionRepository rejectDecisionRepository;

    @Override
    public RejectDecisionResponse save(RejectDecisionRequest rejectDecisionRequest) {
        RejectDecision rejectDecision = rejectDecisionMapper.toEntity(rejectDecisionRequest);
        rejectDecision.setTender(tenderDetailsService.findById(rejectDecisionRequest.tenderId()));
        rejectDecision.setFileMetadata(fileStorageService.findById(rejectDecisionRequest.fileMetadataId()));
        return rejectDecisionMapper.toResponse(rejectDecisionRepository.save(rejectDecision));
    }

}