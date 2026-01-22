package com.flex.tender.payload.response;

public record OfferResponse(
        Integer id, 
        Integer tenderId,
        CompanyProfileResponse companyProfile,
        String status,
        Integer bidPrice,
        CurrencyResponse currency,
        String publication,
        FileMetadataResponse proposition,
        boolean hasAwardDecision,
        boolean hasRejectDecision) {
}