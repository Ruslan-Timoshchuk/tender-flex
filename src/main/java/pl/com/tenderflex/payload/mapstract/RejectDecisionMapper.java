package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import pl.com.tenderflex.model.RejectDecision;
import pl.com.tenderflex.payload.request.RejectDecisionRequest;
import pl.com.tenderflex.payload.response.RejectDecisionResponse;

@Mapper(componentModel = "spring", uses = FileMetadataMapper.class)
public interface RejectDecisionMapper {
    
    RejectDecision toEntity(RejectDecisionRequest rejectDecisionRequest);
    
    RejectDecisionResponse toResponse(RejectDecision rejectDecision);

}