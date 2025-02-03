package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import pl.com.tenderflex.model.AwardDecision;
import pl.com.tenderflex.payload.request.AwardDecisionRequest;
import pl.com.tenderflex.payload.response.AwardDecisionResponse;

@Mapper(componentModel = "spring", uses = FileMetadataMapper.class)
public interface AwardDecisionMapper {
    
    AwardDecision toEntity(AwardDecisionRequest awardDecisionRequest);
    
    AwardDecisionResponse toResponse(AwardDecision awardDecision);

}