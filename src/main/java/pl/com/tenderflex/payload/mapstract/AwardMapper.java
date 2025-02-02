package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import pl.com.tenderflex.model.AwardDecision;
import pl.com.tenderflex.payload.request.AwardDecisionRequest;

@Mapper(componentModel = "spring", uses = FileMetadataMapper.class)
public interface AwardMapper {
    
    AwardDecision toEntity(AwardDecisionRequest awardDecisionRequest);

}