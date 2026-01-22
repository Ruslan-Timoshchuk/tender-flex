package com.flex.tender.payload.request;

public record OfferRequest(
        Integer bidderId,  
        CompanyProfileRequest companyProfile,
        Integer bidPrice, 
        CurrencyRequest currency,
        String publication, 
        FileMetadataRequest proposition) {
}