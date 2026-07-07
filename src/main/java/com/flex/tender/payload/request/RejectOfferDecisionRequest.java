package com.flex.tender.payload.request;

/**
 * @author Ruslan Tymoshchuk
 */
public record RejectOfferDecisionRequest(
        Integer offerId,
        Integer rejectDecisionId) {
}