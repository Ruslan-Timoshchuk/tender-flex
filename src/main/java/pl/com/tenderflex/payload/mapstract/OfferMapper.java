package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.payload.request.OfferDetailsRequest;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    @Mapping(target = "tenderId", source = "tenderId")
    @Mapping(target = "organization.name", source = "organizationName")
    @Mapping(target = "organization.nationalRegistrationNumber", source = "nationalRegistrationNumber")
    @Mapping(target = "organization.country.id", source = "countryId")
    @Mapping(target = "organization.city", source = "city")
    @Mapping(target = "organization.contactPerson.firstName", source = "firstName")
    @Mapping(target = "organization.contactPerson.lastName", source = "lastName")
    @Mapping(target = "organization.contactPerson.phone", source = "phone")
    @Mapping(target = "currency.id", source = "currencyId")
    @Mapping(target = "bidPrice", source = "bidPrice")
    @Mapping(target = "documentUrl", source = "documentUrl")
    Offer offerDetailsRequestToOffer(OfferDetailsRequest offerDetailsRequest);
    
}