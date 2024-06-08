package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.payload.request.TenderDetailsRequest;
import pl.com.tenderflex.payload.response.BidderTenderDetailsResponse;
import pl.com.tenderflex.payload.response.ContractorTenderDetailsResponse;
import pl.com.tenderflex.payload.response.TenderInListResponse;

@Mapper(componentModel = "spring")
public interface TenderMapper {

    @Mapping(target = "contractorCompanyDetails.officialName", source = "officialName")
    @Mapping(target = "contractorCompanyDetails.registrationNumber", source = "registrationNumber")
    @Mapping(target = "contractorCompanyDetails.country.id", source = "countryId")
    @Mapping(target = "contractorCompanyDetails.city", source = "city")
    @Mapping(target = "contactPerson.firstName", source = "firstName")
    @Mapping(target = "contactPerson.lastName", source = "lastName")
    @Mapping(target = "contactPerson.phoneNumber", source = "phoneNumber")
    @Mapping(target = "cpv.id", source = "cpvId")
    @Mapping(target = "type.id", source = "typeOfTenderId")
    @Mapping(target = "details", source = "details")
    @Mapping(target = "maxPrice", source = "maxPrice")
    @Mapping(target = "minPrice", source = "minPrice")
    @Mapping(target = "currency.id", source = "currencyId")
    @Mapping(target = "publication", source = "publication")
    @Mapping(target = "deadline", source = "deadline")
    @Mapping(target = "signedContractDeadline", source = "signedContractDeadline")
    @Mapping(target = "contractFileName", source = "contractFileName")
    @Mapping(target = "awardDecisionFileName", source = "awardDecisionFileName")
    @Mapping(target = "rejectDecisionFileName", source = "rejectDecisionFileName")
    Tender tenderDetailsRequestToTender(TenderDetailsRequest tenderDetailsRequest);
    
    @Mapping(target = "tenderId", source = "tender.id")
    @Mapping(target = "cpvCode", source = "tender.cpv.code")
    @Mapping(target = "cpvDescription", source = "tender.cpv.description")
    @Mapping(target = "officialName", source = "tender.contractorCompanyDetails.officialName")
    @Mapping(target = "tenderStatus", source = "tender.status")
    @Mapping(target = "deadline", source = "tender.deadline", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "offValue", source = "offersAmount")
    TenderInListResponse<Integer> tenderToContractorTenderResponse(Tender tender, Integer offersAmount);

    @Mapping(target = "tenderId", source = "tender.id")
    @Mapping(target = "cpvCode", source = "tender.cpv.code")
    @Mapping(target = "cpvDescription", source = "tender.cpv.description")
    @Mapping(target = "officialName", source = "tender.contractorCompanyDetails.officialName")
    @Mapping(target = "tenderStatus", source = "tender.status")
    @Mapping(target = "deadline", source = "tender.deadline", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "offValue", source = "offerStatus")
    TenderInListResponse<String> tenderToBidderTenderResponse(Tender tender, String offerStatus);
    
    @Mapping(target = "tenderId", source = "id")
    @Mapping(target = "companyName", source = "contractorCompanyDetails.officialName")
    @Mapping(target = "registrationNumber", source = "contractorCompanyDetails.registrationNumber")
    @Mapping(target = "country", source = "contractorCompanyDetails.country.countryName")
    @Mapping(target = "city", source = "contractorCompanyDetails.city")
    @Mapping(target = "firstName", source = "contactPerson.firstName")
    @Mapping(target = "lastName", source = "contactPerson.lastName")
    @Mapping(target = "phone", source = "contactPerson.phoneNumber")
    @Mapping(target = "procedure", constant = "OPEN_PROCEDURE")
    @Mapping(target = "language", constant = "ENGLISH")
    @Mapping(target = "cpvCode", source = "cpv.code")
    @Mapping(target = "cpvDescription", source = "cpv.description")
    @Mapping(target = "type", source = "type.title")
    @Mapping(target = "description", source = "details")
    @Mapping(target = "maxTenderValue", source = "maxPrice")
    @Mapping(target = "minTenderValue", source = "minPrice")
    @Mapping(target = "currency", source = "currency.currencyType")
    @Mapping(target = "publicationDate", source = "publication", dateFormat = "dd/MM/yyyy")
    @Mapping(target = "offerSubmissionDeadline", source = "deadline", dateFormat = "dd/MM/yyyy")
    @Mapping(target = "signedContractSubmissionDeadline", source = "signedContractDeadline", dateFormat = "dd/MM/yyyy")
    @Mapping(target = "contractFileName", source = "contractFileName")
    @Mapping(target = "awardDecisionFileName", source = "awardDecisionFileName")
    @Mapping(target = "rejectDecisionFileName", source = "rejectDecisionFileName")
    ContractorTenderDetailsResponse tenderToContractorTenderDetailsResponse(Tender tender);
    
    @Mapping(target = "tenderId", source = "id")
    @Mapping(target = "language", constant = "ENGLISH")
    @Mapping(target = "cpvCode", source = "cpv.code")
    @Mapping(target = "cpvDescription", source = "cpv.description")
    @Mapping(target = "type", source = "type.title")
    @Mapping(target = "description", source = "details")
    @Mapping(target = "minTenderValue", source = "minPrice")
    @Mapping(target = "currency", source = "currency.currencyType")
    @Mapping(target = "tenderPublicationDate", source = "publication", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "deadlineForOfferSubmission", source = "deadline", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "deadlineForSignedContractSubmission", source = "signedContractDeadline", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "contractFileName", source = "contractFileName")
    BidderTenderDetailsResponse tenderToBidderTenderDetailsResponse(Tender tender);
}