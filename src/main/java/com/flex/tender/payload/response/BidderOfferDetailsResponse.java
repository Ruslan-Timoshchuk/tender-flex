package com.flex.tender.payload.response;

public record BidderOfferDetailsResponse(
        Integer id, 
        CompanyProfileResponse companyProfile,
        Integer bidPrice,
        CurrencyResponse currency,
        String publication,
        FileMetadataResponse proposition,
        String statusName,
        String statusLabel) {
}