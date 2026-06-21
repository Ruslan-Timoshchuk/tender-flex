package com.flex.tender.payload.mapper;

import static com.flex.tender.model.enumeration.EOfferContractorStatus.*;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import com.flex.tender.model.enumeration.EOfferContractorStatus;
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
    
    @Named("contractorStatusName")
    public String toContractorStatusName(EOfferStatus status) {
        return toContractorStatus(status).name();
    }
    
    @Named("contractorStatusLabel")
    public String toContractorLabel(EOfferStatus status) {
        return toContractorStatus(status).getLabel();
    }
    
    private EOfferContractorStatus toContractorStatus(EOfferStatus status) {
        return switch (status) {
            case SENT -> OFFER_RECEIVED;
            case SELECTED -> OFFER_SELECTED;
            case REJECTED_BY_CONTRACTOR -> OFFER_REJECTED_BY_CONTRACTOR;
            case REJECTED_BY_BIDDER -> OFFER_REJECTED_BY_BIDDER;
            case CONTRACT_DECLINED -> CONTRACT_DECLINED_BY_BIDDER;
            case CONTRACT_APPROVED -> CONTRACT_APPROVED_BY_BIDDER;
            default -> throw new IllegalArgumentException("Unexpected value: " + status);
        };
    }
    
}