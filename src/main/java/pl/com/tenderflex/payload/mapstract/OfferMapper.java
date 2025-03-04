package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.enums.EOfferStatus;
import pl.com.tenderflex.payload.request.OfferRequest;
import pl.com.tenderflex.payload.response.OfferResponse;

@Mapper(componentModel = "spring", uses = { TenderMapper.class, CompanyProfileMapper.class })
public interface OfferMapper {

    @Mapping(target = "bidderId", source = "bidderId")
    Offer toEntity(OfferRequest offerRequest);
        
    @Mapping(target = "id", source = "offer.id")
    @Mapping(target = "companyProfileId", source = "offer.companyProfile.id")
    @Mapping(target = "price", source = "offer.bidPrice")
    @Mapping(target = "date", source = "offer.publication", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "status", source = "status")
    OfferResponse toResponse(Offer offer, EOfferStatus status);
       
}