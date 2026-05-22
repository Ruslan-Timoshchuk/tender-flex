package com.flex.tender.payload.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.flex.tender.model.RejectDecision;
import com.flex.tender.payload.request.RejectDecisionRequest;
import com.flex.tender.payload.response.RejectDecisionResponse;

@Mapper(componentModel = "spring", uses = FileMetadataMapper.class)
public interface RejectDecisionMapper {
    
    @Mapping(target = "tender", ignore = true)
    @Mapping(target = "fileMetadataId", ignore = true)
    RejectDecision toEntity(RejectDecisionRequest rejectDecisionRequest);
    
    RejectDecisionResponse toResponse(RejectDecision rejectDecision);

}