package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.enums.EOfferStatus;
import pl.com.tenderflex.payload.request.OfferRequest;
import pl.com.tenderflex.payload.response.OfferResponse;

@Mapper(componentModel = "spring", uses = { TenderMapper.class, CompanyProfileMapper.class, CurrencyMapper.class,
        FileMetadataMapper.class })
public interface OfferMapper {

    @Mapping(target = "bidderId", source = "bidderId")
    Offer toEntity(OfferRequest offerRequest);

    @Mapping(target = "tenderId", source = "offer.tender.id")
    @Mapping(target = "publication", source = "offer.publication", dateFormat = "dd/MM/yyyy")
    OfferResponse toResponse(Offer offer, EOfferStatus status);

}