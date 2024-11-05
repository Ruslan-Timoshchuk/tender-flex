package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.com.tenderflex.model.RejectDecision;
import pl.com.tenderflex.payload.iresponse.response.RejectResponse;
import pl.com.tenderflex.payload.request.RejectRequest;

@Mapper(componentModel = "spring")
public interface RejectMapper {

    @Mapping(target = "tender.id", source = "tenderId")
    @Mapping(target = "rejectFile.id", source = "rejectFileId")
    RejectDecision rejectRequestToRejectDecision(RejectRequest rejectRequest);
    
    RejectResponse rejectDecisionToRejectResponse(RejectDecision rejectDecision);
    
}