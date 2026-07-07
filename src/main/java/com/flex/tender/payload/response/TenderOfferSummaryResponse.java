package com.flex.tender.payload.response;

/**
 * @author Ruslan Timoshchuk
 */
public record TenderOfferSummaryResponse(
        Integer offerId,
        String bidderOfficialName,
        String currencyCode,
        Integer bidPrice,
        String countryName,
        String receivedDate,
        String offerStatusName,
        String offerStatusLabel) {
}