package com.flex.tender.payload.mapstract;

import org.mapstruct.Mapper;

import com.flex.tender.model.RejectDecision;
import com.flex.tender.payload.request.RejectDecisionRequest;
import com.flex.tender.payload.response.RejectDecisionResponse;

@Mapper(componentModel = "spring", uses = FileMetadataMapper.class)
public interface RejectDecisionMapper {
    
    RejectDecision toEntity(RejectDecisionRequest rejectDecisionRequest);
    
    RejectDecisionResponse toResponse(RejectDecision rejectDecision);

}