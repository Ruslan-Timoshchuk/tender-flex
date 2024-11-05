package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.com.tenderflex.model.AwardDecision;
import pl.com.tenderflex.payload.iresponse.response.AwardResponse;
import pl.com.tenderflex.payload.request.AwardRequest;

@Mapper(componentModel = "spring")
public interface AwardMapper {

    @Mapping(target = "tender.id", source = "tenderId")
    @Mapping(target = "awardFile.id", source = "awardFileId")
    AwardDecision awardRequestToAwardDecision(AwardRequest awardRequest);
    
    AwardResponse awardDecisionToAwardResponse(AwardDecision awardDecision);
    
}