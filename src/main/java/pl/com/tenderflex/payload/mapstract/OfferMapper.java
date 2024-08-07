package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.com.tenderflex.model.EOfferStatus;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.User;
import pl.com.tenderflex.payload.iresponse.response.OfferDetailsBidderResponse;
import pl.com.tenderflex.payload.iresponse.response.OfferDetailsContractorResponse;
import pl.com.tenderflex.payload.iresponse.response.OfferInListResponse;
import pl.com.tenderflex.payload.request.OfferDetailsRequest;

@Mapper(componentModel = "spring", uses = TenderMapper.class)
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
        
    @Mapping(target = "offerId", source = "offer.id")
    @Mapping(target = "bidderOficialName", source = "offer.bidderCompanyDetails.officialName")
    @Mapping(target = "country", source = "offer.bidderCompanyDetails.country.countryName")
    @Mapping(target = "fieldOfTheTender", source = "offer.tender.cpv.description")
    @Mapping(target = "price", source = "offer.bidPrice")
    @Mapping(target = "currency", source = "offer.currency.currencyType")
    @Mapping(target = "date", source = "offer.publicationDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "status", source = "status")
    OfferInListResponse offerToOfferInListResponse(Offer offer, EOfferStatus status);
    
    @Mapping(target = "offerId", source = "id")
    @Mapping(target = "tender", source = "tender")
    @Mapping(target = "bidderCompanyName", source = "bidderCompanyDetails.officialName")
    @Mapping(target = "registrationNumber", source = "bidderCompanyDetails.registrationNumber")
    @Mapping(target = "country", source = "bidderCompanyDetails.country.countryName")
    @Mapping(target = "city", source = "bidderCompanyDetails.city")
    @Mapping(target = "firstName", source = "contactPerson.firstName")
    @Mapping(target = "lastName", source = "contactPerson.lastName")
    @Mapping(target = "phone", source = "contactPerson.phoneNumber")
    @Mapping(target = "bidPrice", source = "bidPrice")
    @Mapping(target = "currency", source = "currency.currencyType")
    @Mapping(target = "documentName", source = "documentName")
    @Mapping(target = "awardDecision", source = "awardDecision")
    @Mapping(target = "rejectDecision", source = "rejectDecision")
    @Mapping(target = "status", source = "offerStatusBidder")
    OfferDetailsBidderResponse offerToOfferDetailsBidderResponse(Offer offer);
    
    @Mapping(target = "offerId", source = "id")
    @Mapping(target = "bidderCompanyName", source = "bidderCompanyDetails.officialName")
    @Mapping(target = "registrationNumber", source = "bidderCompanyDetails.registrationNumber")
    @Mapping(target = "country", source = "bidderCompanyDetails.country.countryName")
    @Mapping(target = "city", source = "bidderCompanyDetails.city")
    @Mapping(target = "firstName", source = "contactPerson.firstName")
    @Mapping(target = "lastName", source = "contactPerson.lastName")
    @Mapping(target = "phone", source = "contactPerson.phoneNumber")
    @Mapping(target = "bidPrice", source = "bidPrice")
    @Mapping(target = "currency", source = "currency.currencyType")
    @Mapping(target = "documentName", source = "documentName")
    @Mapping(target = "awardDecision", source = "awardDecision")
    @Mapping(target = "rejectDecision", source = "rejectDecision")
    @Mapping(target = "status", source = "offerStatusContractor")
    OfferDetailsContractorResponse offerToOfferDetailsContractorResponse(Offer offer);
    
}