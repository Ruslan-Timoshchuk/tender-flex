package com.flex.tender.payload.response;

public record OfferDetailsResponse(
        Integer id, 
        Integer tenderId,
        CompanyProfileResponse companyProfile,
        Integer bidPrice,
        CurrencyResponse currency,
        String publication,
        FileMetadataResponse proposition,
        String statusName,
        String statusLabel) {
}