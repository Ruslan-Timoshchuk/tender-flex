package pl.com.tenderflex.payload.iresponse.response;

import lombok.Data;
import pl.com.tenderflex.payload.iresponse.OfferDetails;

@Data
public class OfferDetailsContractorResponse implements OfferDetails {

    private Integer offerId;
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
    private String status;
     
}