package com.flex.tender.payload.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.flex.tender.model.Cpv;
import com.flex.tender.model.Offer;
import com.flex.tender.payload.request.OfferRequest;
import com.flex.tender.payload.response.OfferDetailsResponse;
import com.flex.tender.payload.response.OfferSummaryResponse;

@Mapper(componentModel = "spring", uses = { FileMetadataMapper.class, OfferStatusLabelMapper.class })
public interface OfferMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "tender", ignore = true)
    @Mapping(target = "companyProfile", ignore = true)
    @Mapping(target = "currency", ignore = true)
    @Mapping(target = "proposition", ignore = true)
    @Mapping(target = "publication", source = "publication", dateFormat = "yyyy-MM-dd")
    Offer toEntity(OfferRequest offerRequest);

    OfferDetailsResponse toResponse(Offer offer);
    
    @Mapping(target = "id", source = "offer.id")
    @Mapping(target = "cpvCode", source = "cpv.code")
    @Mapping(target = "fieldOfTheTender", source = "cpv.summary")
    @Mapping(target = "submissionDate", source = "offer.publication", dateFormat = "dd/MM/yyyy")
    @Mapping(target = "offerStatusLabel", source = "offer.globalStatus", qualifiedByName = "bidderLabel")
    OfferSummaryResponse toBidderSummaryResponse(Offer offer, Cpv cpv);

    @Mapping(target = "id", source = "offer.id")
    @Mapping(target = "cpvCode", source = "cpv.code")
    @Mapping(target = "fieldOfTheTender", source = "cpv.summary")
    @Mapping(target = "submissionDate", source = "offer.publication", dateFormat = "dd/MM/yyyy")
    @Mapping(target = "offerStatusLabel", source = "offer.globalStatus", qualifiedByName = "contractorLabel")
    OfferSummaryResponse toContractorSummaryResponse(Offer offer, Cpv cpv);
   
}