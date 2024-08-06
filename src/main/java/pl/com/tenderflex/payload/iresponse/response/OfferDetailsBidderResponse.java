package pl.com.tenderflex.payload.response;

import lombok.Data;

@Data
public class OfferDetailsResponse {

    private Integer offerId;
    private BidderTenderDetailsResponse tender;
    private String contractorSt;
    private String bidderSt;
    private String bidderCompanyName;
    private String registrationNumber;
    private String country;
    private String city;
    private String firstName;
    private String lastName;
    private String phone;
    private String bidPrice;
    private String currency;
    private String documentName;
    private String awardDecision;
    private String rejectDecision;

}