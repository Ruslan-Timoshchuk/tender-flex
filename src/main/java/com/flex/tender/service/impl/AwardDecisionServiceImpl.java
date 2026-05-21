package com.flex.tender.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.AwardDecision;
import com.flex.tender.model.FileMetadata;
import com.flex.tender.model.Tender;
import com.flex.tender.payload.mapper.AwardDecisionMapper;
import com.flex.tender.payload.request.AwardDecisionRequest;
import com.flex.tender.payload.response.AwardDecisionResponse;
import com.flex.tender.repository.AwardDecisionRepository;
import com.flex.tender.service.AwardDecisionService;
import com.flex.tender.service.FileStorageService;
import com.flex.tender.service.TenderDetailsService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AwardDecisionServiceImpl implements AwardDecisionService {

    private final AwardDecisionMapper awardDecisionMapper;
    private final AwardDecisionRepository awardDecisionRepository;
    private final TenderDetailsService tenderDetailsService;
    private final FileStorageService fileStorageService;

    @Override
    @Transactional
    public AwardDecisionResponse save(AwardDecisionRequest awardDecisionRequest) {
        AwardDecision awardDecision = awardDecisionMapper.toEntity(awardDecisionRequest);
        Tender tender = tenderDetailsService.findById(awardDecisionRequest.tenderId());
        FileMetadata fileMetadata = fileStorageService.findById(awardDecisionRequest.filemetadataId());
        awardDecision.setTender(tender);
        awardDecision.setFileMetadata(fileMetadata);
        return awardDecisionMapper.toResponse(awardDecisionRepository.save(awardDecision));
    }

    @Override
    public AwardDecision findById(Integer id) {
        return awardDecisionRepository.findById(id);
    }

}