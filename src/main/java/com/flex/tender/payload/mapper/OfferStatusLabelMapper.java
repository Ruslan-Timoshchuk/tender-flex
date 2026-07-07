package com.flex.tender.payload.mapper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import com.flex.tender.model.enumeration.EOfferBidderStatus;
import com.flex.tender.model.enumeration.EOfferContractorStatus;
import com.flex.tender.model.enumeration.EOfferStatus;

@Component
public class OfferStatusLabelMapper {
    
    @Named("bidderStatusName")
    public String toBidderStatusName(EOfferStatus status) {
        return toBidderStatus(status).name();
    }
    
    @Named("bidderStatusLabel")
    public String toBidderLabel(EOfferStatus status) {
        return toBidderStatus(status).getLabel();
    }
    
    @Named("contractorStatusName")
    public String toContractorStatusName(EOfferStatus status) {
        return toContractorStatus(status).name();
    }
    
    @Named("contractorStatusLabel")
    public String toContractorLabel(EOfferStatus status) {
        return toContractorStatus(status).getLabel();
    }
    
    private EOfferBidderStatus toBidderStatus(EOfferStatus status) {
        return switch (status) {
            case NOT_SENT -> EOfferBidderStatus.OFFER_HAS_NOT_SENT;
            case SENT -> EOfferBidderStatus.OFFER_SENT;
            case SELECTED -> EOfferBidderStatus.OFFER_SELECTED_BY_CONTRACTOR;
            case REJECTED_BY_CONTRACTOR -> EOfferBidderStatus.OFFER_REJECTED_BY_CONTRACTOR;
            case REJECTED_BY_BIDDER -> EOfferBidderStatus.OFFER_REJECTED_BY_BIDDER;
            case CONTRACT_DECLINED -> EOfferBidderStatus.CONTRACT_DECLINED_BY_BIDDER;
            case CONTRACT_APPROVED -> EOfferBidderStatus.CONTRACT_APPROVED_BY_BIDDER;
        };
    }
    
    private EOfferContractorStatus toContractorStatus(EOfferStatus status) {
        return switch (status) {
            case SENT -> EOfferContractorStatus.OFFER_RECEIVED;
            case SELECTED -> EOfferContractorStatus.OFFER_SELECTED;
            case REJECTED_BY_CONTRACTOR -> EOfferContractorStatus.OFFER_REJECTED_BY_CONTRACTOR;
            case REJECTED_BY_BIDDER -> EOfferContractorStatus.OFFER_REJECTED_BY_BIDDER;
            case CONTRACT_DECLINED -> EOfferContractorStatus.CONTRACT_DECLINED_BY_BIDDER;
            case CONTRACT_APPROVED -> EOfferContractorStatus.CONTRACT_APPROVED_BY_BIDDER;
            default -> throw new IllegalArgumentException("Unexpected value: " + status);
        };
    }
    
}