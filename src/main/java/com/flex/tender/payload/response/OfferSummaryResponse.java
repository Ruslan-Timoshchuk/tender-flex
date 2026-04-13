package com.flex.tender.payload.response;

/**
 * @author Ruslan Timoshchuk
 */
public record OfferSummaryResponse(
        Integer id, 
        Integer tenderId,
        String officialName,
        String fieldOfTheTender,
        Integer bidPrice,
        CountryResponse country,
        CurrencyResponse currency,
        String submissionDate,
        String offerStatusLabel) {
}