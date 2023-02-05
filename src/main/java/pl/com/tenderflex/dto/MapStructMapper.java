package pl.com.tenderflex.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import pl.com.tenderflex.dao.OfferRepository;
import pl.com.tenderflex.dao.TenderRepository;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.Tender;

@Mapper(componentModel = "spring")
public abstract class MapStructMapper {

    @Autowired
    protected TenderRepository tenderRepository;
    @Autowired
    protected OfferRepository offerRepository;

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
    public abstract Tender tenderDetailsRequestToTender(TenderDetailsRequest tenderDetailsRequest);

    @Mapping(target = "tenderId", source = "id")
    @Mapping(target = "cpvCode", source = "cpvCode")
    @Mapping(target = "organizationName", source = "tender.organization.name")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "deadline", source = "deadline", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "offersAmount", expression = "java(offerRepository.countOffersByTender(tender.getId()))")
    public abstract ContractorTenderResponse tenderToContractorTenderResponse(Tender tender);

    @Mapping(target = "tenderId", source = "id")
    @Mapping(target = "cpvCode", source = "cpvCode")
    @Mapping(target = "organizationName", source = "tender.organization.name")
    @Mapping(target = "tenderStatus", source = "status")
    @Mapping(target = "deadline", source = "deadline", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "offerStatus", expression = "java(tenderRepository.getOfferStatusForBidder(tender.getId()))")
    public abstract BidderTenderResponse tenderToBidderTenderResponse(Tender tender);

    @Mapping(target = "tender.id", source = "tenderId")
    @Mapping(target = "organization.name", source = "organizationName")
    @Mapping(target = "organization.nationalRegistrationNumber", source = "nationalRegistrationNumber")
    @Mapping(target = "organization.country", source = "country")
    @Mapping(target = "organization.city", source = "city")
    @Mapping(target = "organization.contactPerson.firstName", source = "firstName")
    @Mapping(target = "organization.contactPerson.lastName", source = "lastName")
    @Mapping(target = "organization.contactPerson.phone", source = "phone")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "bidPrice", source = "bidPrice")
    public abstract Offer offerDetailsRequestToOffer(OfferDetailsRequest offerDetailsRequest);

    @Mapping(target = "offerId", source = "id")
    @Mapping(target = "organizationNameByBidder", source = "organization.name")
    @Mapping(target = "spvCode", source = "tender.cpvCode")
    @Mapping(target = "price", source = "bidPrice")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "country", source = "organization.country")
    @Mapping(target = "receivedDate", source = "receivedDate")
    @Mapping(target = "status", source = "contractorStatus")
    public abstract ContractorOfferResponse offerToContractorOfferResponse(Offer offer);

}