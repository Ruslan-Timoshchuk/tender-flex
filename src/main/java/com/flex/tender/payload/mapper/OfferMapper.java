package com.flex.tender.payload.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.flex.tender.model.Cpv;
import com.flex.tender.model.Offer;
import com.flex.tender.payload.request.OfferRequest;
import com.flex.tender.payload.response.OfferDetailsResponse;
import com.flex.tender.payload.response.OfferSummaryResponse;

@Mapper(componentModel = "spring", uses = { CompanyProfileMapper.class, CountryMapper.class, CurrencyMapper.class,
        FileMetadataMapper.class, OfferStatusLabelMapper.class })
public interface OfferMapper {

    @Mapping(target = "bidderId", source = "bidderId")
    Offer toEntity(OfferRequest offerRequest);

    OfferDetailsResponse toResponse(Offer offer);
    
    @Mapping(target = "id", source = "offer.id")
    @Mapping(target = "fieldOfTheTender", source = "cpv.summary")
    @Mapping(target = "submissionDate", source = "offer.publication", dateFormat = "dd/MM/yyyy")
    @Mapping(target = "offerStatusLabel", source = "offer.globalStatus", qualifiedByName = "bidderLabel")
    OfferSummaryResponse toBidderSummaryResponse(Offer offer, Cpv cpv);

    @Mapping(target = "id", source = "offer.id")
    @Mapping(target = "fieldOfTheTender", source = "cpv.summary")
    @Mapping(target = "submissionDate", source = "offer.publication", dateFormat = "dd/MM/yyyy")
    @Mapping(target = "offerStatusLabel", source = "offer.globalStatus", qualifiedByName = "contractorLabel")
    OfferSummaryResponse toContractorSummaryResponse(Offer offer, Cpv cpv);
   
}