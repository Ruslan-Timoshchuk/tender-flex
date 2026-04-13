package com.flex.tender.model;

import java.time.LocalDate;
import com.flex.tender.model.enumeration.EOfferStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Offer {

    private Integer id;
    private Integer bidderId;
    private Tender tender;
    private Contract contract;
    private CompanyProfile companyProfile;
    private EOfferStatus globalStatus;
    private Integer bidPrice;
    private Currency currency;
    private LocalDate publication;
    private FileMetadata proposition;
    private AwardDecision awardDecision;
    private RejectDecision rejectDecision;
 
}