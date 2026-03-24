package com.flex.tender.payload.mapstract;

import org.mapstruct.Mapper;

import com.flex.tender.model.AwardDecision;
import com.flex.tender.payload.request.AwardDecisionRequest;
import com.flex.tender.payload.response.AwardDecisionResponse;

@Mapper(componentModel = "spring", uses = FileMetadataMapper.class)
public interface AwardDecisionMapper {
    
    AwardDecision toEntity(AwardDecisionRequest awardDecisionRequest);
    
    AwardDecisionResponse toResponse(AwardDecision awardDecision);

}