package com.flex.tender.payload.mapstract;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.flex.tender.model.Tender;
import com.flex.tender.model.enumeration.ETenderStatus;
import com.flex.tender.payload.request.TenderRequest;
import com.flex.tender.payload.response.TenderResponse;

@Mapper(componentModel = "spring", uses = { CpvMapper.class, CompanyProfileMapper.class })
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