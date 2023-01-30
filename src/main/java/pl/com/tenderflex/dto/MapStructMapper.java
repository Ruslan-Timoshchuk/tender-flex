package pl.com.tenderflex.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.com.tenderflex.model.Tender;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    @Mapping(target = "organization.name", source = "organizationName")
    @Mapping(target = "organization.nationalRegistrationNumber", source = "nationalRegistrationNumber")
    @Mapping(target = "organization.country", source = "country")
    @Mapping(target = "organization.city", source = "city")
    @Mapping(target = "organization.contactPerson.firstName", source = "firstName")
    @Mapping(target = "organization.contactPerson.lastName", source = "lastName")
    @Mapping(target = "organization.contactPerson.phone", source = "phone")
    @Mapping(target = "cpvCode", source = "cpvCode")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "details", source = "details")
    @Mapping(target = "minPrice", source = "minPrice")
    @Mapping(target = "maxPrice", source = "maxPrice")
    @Mapping(target = "currency", source = "currency")  
    @Mapping(target = "deadline", source = "deadline", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "deadlineForSignedContract", source = "deadlineForSignedContract", dateFormat = "dd-MM-yyyy")
    Tender tenderDetailsRequestToTender(TenderDetailsRequest tenderDetailsRequest);

    @Mapping(target = "organizationName", source = "tender.organization.name")
    @Mapping(target = "nationalRegistrationNumber", source = "tender.organization.nationalRegistrationNumber")
    @Mapping(target = "country", source = "tender.organization.country")
    @Mapping(target = "city", source = "tender.organization.city")
    @Mapping(target = "firstName", source = "tender.organization.contactPerson.firstName")
    @Mapping(target = "lastName", source = "tender.organization.contactPerson.lastName")
    @Mapping(target = "phone", source = "tender.organization.contactPerson.phone")
    @Mapping(target = "cpvCode", source = "cpvCode")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "details", source = "details")
    @Mapping(target = "minPrice", source = "minPrice")
    @Mapping(target = "maxPrice", source = "maxPrice")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "publication", source = "publication", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "deadline", source = "deadline", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "deadlineForSignedContract", source = "deadlineForSignedContract", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "status", source = "status")
    TenderDetailsResponse tenderToTenderDetailsResponse(Tender tender);

}