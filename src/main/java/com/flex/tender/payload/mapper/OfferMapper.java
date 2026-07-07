package com.flex.tender.payload.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.flex.tender.model.Offer;
import com.flex.tender.model.Tender;
import com.flex.tender.payload.request.OfferRequest;
import com.flex.tender.payload.response.ContractorOfferDetailsResponse;
import com.flex.tender.payload.response.BidderOfferDetailsResponse;
import com.flex.tender.payload.response.OfferSummaryResponse;
import com.flex.tender.payload.response.TenderOfferSummaryResponse;

@Mapper(componentModel = "spring", uses = { CompanyProfileMapper.class, CpvMapper.class, CurrencyMapper.class,
        FileMetadataMapper.class, OfferStatusLabelMapper.class })
public interface OfferMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "tender", ignore = true)
    @Mapping(target = "companyProfile", ignore = true)
    @Mapping(target = "currency", ignore = true)
    @Mapping(target = "proposition", ignore = true)
    @Mapping(target = "publication", source = "publication", dateFormat = "yyyy-MM-dd")
    Offer toEntity(OfferRequest offerRequest);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "companyProfile", source = "companyProfile")
    @Mapping(target = "bidPrice", source = "bidPrice")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "publication", source = "publication", dateFormat = "dd/MM/yyyy")
    @Mapping(target = "proposition", source = "proposition")
    @Mapping(target = "statusName", source = "globalStatus", qualifiedByName = "bidderStatusName")
    @Mapping(target = "statusLabel", source = "globalStatus", qualifiedByName = "bidderStatusLabel")
    BidderOfferDetailsResponse toBidderDetails(Offer offer);
    
    @Mapping(target = "id", source = "id")
    @Mapping(target = "companyProfile", source = "companyProfile")
    @Mapping(target = "bidPrice", source = "bidPrice")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "publication", source = "publication", dateFormat = "dd/MM/yyyy")
    @Mapping(target = "proposition", source = "proposition")
    @Mapping(target = "statusName", source = "globalStatus", qualifiedByName = "contractorStatusName")
    @Mapping(target = "statusLabel", source = "globalStatus", qualifiedByName = "contractorStatusLabel")
    ContractorOfferDetailsResponse toContractorDetails(Offer offer);
    
    BidderOfferDetailsResponse toResponse(Offer offer);
    
    @Mapping(target = "offerId", source = "offer.id")
    @Mapping(target = "tenderId", source = "tender.id")
    @Mapping(target = "companyProfile", source = "offer.companyProfile")
    @Mapping(target = "cpvOfTheTender", source = "tender.cpv")
    @Mapping(target = "bidPrice", source = "offer.bidPrice")
    @Mapping(target = "currency", source = "offer.currency")
    @Mapping(target = "submissionDate", source = "offer.publication", dateFormat = "dd/MM/yyyy")
    @Mapping(target = "offerStatusName", source = "offer.globalStatus", qualifiedByName = "bidderStatusName")
    @Mapping(target = "offerStatusLabel", source = "offer.globalStatus", qualifiedByName = "bidderStatusLabel")
    OfferSummaryResponse toBidderSummaryResponse(Offer offer, Tender tender);

    @Mapping(target = "offerId", source = "offer.id")
    @Mapping(target = "tenderId", source = "tender.id")
    @Mapping(target = "companyProfile", source = "offer.companyProfile")
    @Mapping(target = "cpvOfTheTender", source = "tender.cpv")
    @Mapping(target = "bidPrice", source = "offer.bidPrice")
    @Mapping(target = "currency", source = "offer.currency")
    @Mapping(target = "submissionDate", source = "offer.publication", dateFormat = "dd/MM/yyyy")
    @Mapping(target = "offerStatusName", source = "offer.globalStatus", qualifiedByName = "contractorStatusName")
    @Mapping(target = "offerStatusLabel", source = "offer.globalStatus", qualifiedByName = "contractorStatusLabel")
    OfferSummaryResponse toContractorSummaryResponse(Offer offer, Tender tender);
   
    @Mapping(target = "offerId", source = "id") 
    @Mapping(target = "bidderOfficialName", source = "companyProfile.officialName")
    @Mapping(target = "currencyCode", source = "currency.code")
    @Mapping(target = "bidPrice", source = "bidPrice")
    @Mapping(target = "countryName", source = "companyProfile.country.name")
    @Mapping(target = "receivedDate", source = "publication", dateFormat = "dd/MM/yyyy")
    @Mapping(target = "offerStatusName", source = "globalStatus", qualifiedByName = "contractorStatusName")
    @Mapping(target = "offerStatusLabel", source = "globalStatus", qualifiedByName = "contractorStatusLabel")
    TenderOfferSummaryResponse toTenderSummaryResponse(Offer offer);

}