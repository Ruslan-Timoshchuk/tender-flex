package com.flex.tender.payload.request;

/**
 * @author Ruslan Tymoshchuk
 */
public record AwardOfferDecisionRequest( 
        Integer offerId, 
        Integer awardDecisionId) {
}