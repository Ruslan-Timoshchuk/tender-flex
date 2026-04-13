package com.flex.tender.payload.mapper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import com.flex.tender.model.enumeration.EOfferStatus;

@Component
public class OfferStatusLabelMapper {

    @Named("bidderLabel")
    public String toBidderLabel(EOfferStatus status) {
        return switch (status) {
            case NOT_SENT -> "Offer hasn't sent";
            case SENT -> "Offer sent to Contractor";
            case SELECTED -> "Offer selected by Contractor";
            case REJECTED_BY_CONTRACTOR -> "Offer rejected by Contractor";
            case REJECTED_BY_BIDDER -> "Offer rejected by Bidder";
            case CONTRACT_DECLINED -> "Contract declined by Bidder";
            case CONTRACT_APPROVED -> "Contract approved by Bidder";
        };
    }
    
    @Named("contractorLabel")
    public String toContractorLabel(EOfferStatus status) {
        return switch (status) {
            case SENT -> "Offer received";
            case SELECTED -> "Offer selected";
            case REJECTED_BY_CONTRACTOR -> "Offer rejected by Contractor";
            case REJECTED_BY_BIDDER -> "Offer rejected by Bidder";
            case CONTRACT_DECLINED -> "Contract declined by Bidder";
            case CONTRACT_APPROVED -> "Contract approved by Bidder";
            default -> throw new IllegalArgumentException("Unexpected value: " + status);
        };
    }
    
}