package com.flex.tender.payload.request;

/**
 * @author Ruslan Tymoshchuk
 */
public record ApproveContractDecisionRequest(
        Integer offerId,
        Integer awardDecisionId) {
}