package com.flex.tender.payload.request;

/**
 * @author Ruslan Tymoshchuk
 */
public record DeclineContractDecisionRequest(
        Integer offerId,
        Integer awardDecisionId) {
}