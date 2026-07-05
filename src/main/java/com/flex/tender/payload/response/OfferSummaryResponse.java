package com.flex.tender.payload.response;

/**
 * @author Ruslan Timoshchuk
 */
public record OfferSummaryResponse(
        Integer offerId, 
        Integer tenderId,
        CompanyProfileResponse companyProfile,
        CpvResponse cpvOfTheTender,
        Integer bidPrice,
        CurrencyResponse currency,
        String submissionDate,
        String offerStatusName,
        String offerStatusLabel) {
}