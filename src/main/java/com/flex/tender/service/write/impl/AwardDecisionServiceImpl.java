package com.flex.tender.service.write.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.AwardDecision;
import com.flex.tender.model.FileMetadata;
import com.flex.tender.payload.mapper.AwardDecisionMapper;
import com.flex.tender.payload.request.AwardDecisionRequest;
import com.flex.tender.repository.AwardDecisionRepository;
import com.flex.tender.service.read.FileStorageDetailsService;
import com.flex.tender.service.write.AwardDecisionService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AwardDecisionServiceImpl implements AwardDecisionService {

    private final AwardDecisionMapper awardDecisionMapper;
    private final AwardDecisionRepository awardDecisionRepository;
    private final FileStorageDetailsService fileStorageDetailsService;
    
    @Override
    public AwardDecision buildEntity(AwardDecisionRequest awardDecisionRequest) {
        AwardDecision awardDecision = awardDecisionMapper.toEntity(awardDecisionRequest);
        FileMetadata awardDecisionFile = fileStorageDetailsService.findById(awardDecisionRequest.fileMetadataId());
        awardDecision.setFileMetadata(awardDecisionFile);
        return awardDecision;
    }
    
    @Override
    public AwardDecision save(AwardDecision awardDecision) {
        return awardDecisionRepository.save(awardDecision);
    }

}