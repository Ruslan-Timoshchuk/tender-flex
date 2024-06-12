package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.User;
import pl.com.tenderflex.payload.request.OfferDetailsRequest;
import pl.com.tenderflex.payload.response.OfferDetailsResponse;
import pl.com.tenderflex.payload.response.OfferResponse;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    @Mapping(target = "bidder.id", source = "bidder.id")
    @Mapping(target = "tender.id", source = "offerDetails.tenderId")
    @Mapping(target = "bidderCompanyDetails.officialName", source = "offerDetails.company")
    @Mapping(target = "bidderCompanyDetails.registrationNumber", source = "offerDetails.registrationNumber")
    @Mapping(target = "bidderCompanyDetails.country.id", source = "offerDetails.countryId")
    @Mapping(target = "bidderCompanyDetails.city", source = "offerDetails.city")
    @Mapping(target = "contactPerson.firstName", source = "offerDetails.firstName")
    @Mapping(target = "contactPerson.lastName", source = "offerDetails.lastName")
    @Mapping(target = "contactPerson.phoneNumber", source = "offerDetails.phoneNumber")
    @Mapping(target = "bidPrice", source = "offerDetails.bidPrice")
    @Mapping(target = "currency.id", source = "offerDetails.currencyId")
    @Mapping(target = "publicationDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "documentName", source = "offerDetails.documentName")
    @Mapping(target = "offerStatusBidder", expression = "java(pl.com.tenderflex.model.EOfferStatus.OFFER_SENT_TO_CONTRACTOR)")
    @Mapping(target = "offerStatusContractor", expression = "java(pl.com.tenderflex.model.EOfferStatus.OFFER_RECEIVED)")
    Offer newOfferRequestToOffer(OfferDetailsRequest offerDetails, User bidder);
        
    @Mapping(target = "offerId", source = "id")
    @Mapping(target = "price", source = "bidPrice")
    @Mapping(target = "currency", source = "currency.currencyType")
    @Mapping(target = "date", source = "publicationDate")
    OfferResponse offerToOfferResponse(Offer offer);
    
    @Mapping(target = "offerId", source = "id")
    @Mapping(target = "bidPrice", source = "bidPrice")
    @Mapping(target = "currency", source = "currency.currencyType")
    @Mapping(target = "documentName", source = "documentName")
    @Mapping(target = "awardDecision", source = "awardDecision")
    @Mapping(target = "rejectDecision", source = "rejectDecision")
    OfferDetailsResponse offerToOfferDetailsResponse(Offer offer);
    
}