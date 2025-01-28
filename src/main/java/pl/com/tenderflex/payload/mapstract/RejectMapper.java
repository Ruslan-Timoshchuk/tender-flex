package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import pl.com.tenderflex.model.RejectDecision;
import pl.com.tenderflex.payload.request.RejectDecisionRequest;

@Mapper(componentModel = "spring", uses = FileMetadataMapper.class)
public interface RejectMapper {
    
    RejectDecision toEntity(RejectDecisionRequest rejectDecisionRequest);

}