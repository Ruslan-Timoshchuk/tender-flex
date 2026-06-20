package com.flex.tender.model.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EOfferContractorStatus {

    OFFER_RECEIVED("Offer received"),
    OFFER_SELECTED("Offer selected"),
    OFFER_REJECTED_BY_CONTRACTOR("Offer rejected by Contractor"),
    OFFER_REJECTED_BY_BIDDER("Offer rejected by Bidder"),
    CONTRACT_DECLINED_BY_BIDDER("Contract declined by Bidder"),
    CONTRACT_APPROVED_BY_BIDDER("Contract approved by Bidder");

    private final String label;
    
}