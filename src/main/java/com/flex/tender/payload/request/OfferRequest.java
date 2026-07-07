package com.flex.tender.payload.request;

public record OfferRequest(
        Integer id,
        Integer tenderId,
        CompanyProfileRequest companyProfile,
        Integer bidPrice, 
        Integer currencyId,
        String publication, 
        Integer propositionMetadataId) {
}