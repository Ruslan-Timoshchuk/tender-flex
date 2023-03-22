package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.payload.request.OfferDetailsRequest;
import pl.com.tenderflex.payload.response.OfferDetailsResponse;
import pl.com.tenderflex.payload.response.OfferResponse;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    @Mapping(target = "tender.id", source = "tenderId")
    @Mapping(target = "organization.name", source = "organizationName")
    @Mapping(target = "organization.nationalRegistrationNumber", source = "nationalRegistrationNumber")
    @Mapping(target = "organization.country.id", source = "countryId")
    @Mapping(target = "organization.city", source = "city")
    @Mapping(target = "organization.contactPerson.firstName", source = "firstName")
    @Mapping(target = "organization.contactPerson.lastName", source = "lastName")
    @Mapping(target = "organization.contactPerson.phone", source = "phone")
    @Mapping(target = "currency.id", source = "currencyId")
    @Mapping(target = "bidPrice", source = "bidPrice")
    @Mapping(target = "documentName", source = "documentName")
    Offer offerDetailsRequestToOffer(OfferDetailsRequest offerDetailsRequest);
    
    @Mapping(target = "offerId", source = "id")
    @Mapping(target = "organizationNameByBidder", source = "organization.name")
    @Mapping(target = "spvCode", source = "tender.cpvCode")
    @Mapping(target = "price", source = "bidPrice")
    @Mapping(target = "country", source = "organization.country.countryName")
    @Mapping(target = "date", source = "publicationDate")
    @Mapping(target = "status", source = "bidderStatus")
    OfferResponse offerToOfferBidderResponse(Offer offer);
    
    @Mapping(target = "offerId", source = "id")
    @Mapping(target = "organizationNameByBidder", source = "organization.name")
    @Mapping(target = "spvCode", source = "tender.cpvCode")
    @Mapping(target = "price", source = "bidPrice")
    @Mapping(target = "country", source = "organization.country.countryName")
    @Mapping(target = "date", source = "publicationDate")
    @Mapping(target = "status", source = "contractorStatus")
    OfferResponse offerToOfferContractorResponse(Offer offer);
    
    @Mapping(target = "offerId", source = "id")
    @Mapping(target = "organizationNameByBidder", source = "organization.name")
    @Mapping(target = "nationalRegistrationNumber", source = "organization.nationalRegistrationNumber")
    @Mapping(target = "country", source = "organization.country.countryName")
    @Mapping(target = "city", source = "organization.city")
    @Mapping(target = "firstName", source = "organization.contactPerson.firstName")
    @Mapping(target = "lastName", source = "organization.contactPerson.lastName")
    @Mapping(target = "phone", source = "organization.contactPerson.phone")
    @Mapping(target = "bidPrice", source = "bidPrice")
    @Mapping(target = "currency", source = "currency.currencyType")
    @Mapping(target = "documentName", source = "documentName")
    OfferDetailsResponse offerToOfferDetailsResponse(Offer offer);
    
}