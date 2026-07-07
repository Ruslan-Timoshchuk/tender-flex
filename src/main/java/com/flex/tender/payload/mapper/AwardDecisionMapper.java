package com.flex.tender.payload.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.flex.tender.model.AwardDecision;
import com.flex.tender.payload.request.AwardDecisionRequest;
import com.flex.tender.payload.response.AwardDecisionResponse;

@Mapper(componentModel = "spring", uses = FileMetadataMapper.class)
public interface AwardDecisionMapper {
    
    @Mapping(target = "tender", ignore = true)
    @Mapping(target = "fileMetadata", ignore = true)
    AwardDecision toEntity(AwardDecisionRequest awardDecisionRequest);
    
    AwardDecisionResponse toResponse(AwardDecision awardDecision);

}