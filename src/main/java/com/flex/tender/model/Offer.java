package com.flex.tender.model;

import java.time.LocalDate;
import com.flex.tender.model.enumeration.EOfferStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Offer {

    private Integer id;
    private User bidder;
    private Tender tender;
    private CompanyProfile companyProfile;
    private EOfferStatus globalStatus;
    private Integer bidPrice;
    private Currency currency;
    private LocalDate publication;
    private FileMetadata proposition;
    private AwardDecision awardDecision;
    private RejectDecision rejectDecision;
 
}