package com.flex.tender.payload.response;

/**
 * @author Ruslan Timoshchuk
 */
public record OfferSummaryResponse(
        Integer id, 
        CompanyProfileResponse companyProfile,
        CpvResponse cpvOfTheTender,
        Integer bidPrice,
        CurrencyResponse currency,
        String submissionDate,
        String offerStatusName,
        String offerStatusLabel) {
}