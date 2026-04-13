package com.flex.tender.payload.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.flex.tender.model.Offer;
import com.flex.tender.payload.request.OfferRequest;
import com.flex.tender.payload.response.OfferResponse;
import com.flex.tender.payload.response.OfferSummaryResponse;

@Mapper(componentModel = "spring", uses = { CompanyProfileMapper.class, CountryMapper.class, CurrencyMapper.class,
        FileMetadataMapper.class, OfferStatusLabelMapper.class })
public interface OfferMapper {

    @Mapping(target = "bidderId", source = "bidderId")
    Offer toEntity(OfferRequest offerRequest);

    @Mapping(target = "tenderId", source = "offer.tender.id")
    @Mapping(target = "submissionDate", source = "publication", dateFormat = "dd/MM/yyyy")
    @Mapping(target = "offerStatusLabel", source = "globalStatus", qualifiedByName = "bidderLabel")
    OfferSummaryResponse toBidderSummaryResponse(Offer offer);

    @Mapping(target = "submissionDate", source = "publication", dateFormat = "dd/MM/yyyy")
    @Mapping(target = "offerStatusLabel", source = "globalStatus", qualifiedByName = "contractorLabel")
    OfferSummaryResponse toContractorSummaryResponse(Offer offer);

    OfferResponse toBidderResponse(Offer offer);
    
    OfferResponse toContractorResponse(Offer offer);
    
}