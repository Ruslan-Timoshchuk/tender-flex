package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.model.enums.ETenderStatus;
import pl.com.tenderflex.payload.request.TenderRequest;
import pl.com.tenderflex.payload.response.TenderResponse;

@Mapper(componentModel = "spring", uses = { CpvMapper.class, CompanyProfileMapper.class, ContractMapper.class,
        AwardDecisionMapper.class, RejectDecisionMapper.class })
public interface TenderMapper {

    @Mapping(target = "publicationDate", source = "publication", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "offerSubmissionDeadline", source = "offerSubmissionDeadline", dateFormat = "yyyy-MM-dd")
    Tender toEntity(TenderRequest tenderRequest);

    @Mapping(target = "contractId", source = "tender.contract.id")
    @Mapping(target = "publicationDate", source = "tender.publicationDate", dateFormat = "dd/MM/yyyy")
    @Mapping(target = "offerSubmissionDeadline", source = "tender.offerSubmissionDeadline", dateFormat = "dd/MM/yyyy")
    @Mapping(target = "status", source = "status")
    TenderResponse toResponse(Tender tender, ETenderStatus status);

}