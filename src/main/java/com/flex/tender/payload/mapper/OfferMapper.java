package com.flex.tender.payload.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.flex.tender.model.Offer;
import com.flex.tender.model.enumeration.EOfferStatus;
import com.flex.tender.payload.request.OfferRequest;
import com.flex.tender.payload.response.OfferResponse;

@Mapper(componentModel = "spring", uses = { CompanyProfileMapper.class, CurrencyMapper.class,
        FileMetadataMapper.class })
public interface OfferMapper {

    @Mapping(target = "bidderId", source = "bidderId")
    Offer toEntity(OfferRequest offerRequest);

    @Mapping(target = "tenderId", source = "offer.tender.id")
    @Mapping(target = "publication", source = "offer.publication", dateFormat = "dd/MM/yyyy")
    OfferResponse toResponse(Offer offer, EOfferStatus status, boolean hasAwardDecision, boolean hasRejectDecision);

}