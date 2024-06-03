package pl.com.tenderflex.payload.request;

import lombok.Data;

@Data
public class TenderDetailsRequest {

    private String officialName;
    private String registrationNumber;
    private Integer countryId;
    private String city;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Integer cpvId;
    private Integer typeOfTenderId;
    private String details;
    private Integer maxPrice;
    private Integer minPrice;
    private Integer currencyId;
    private String publication;
    private String deadline;
    private String signedContractDeadline;
    private String contractFileName;
    private String awardDecisionFileName;
    private String rejectDecisionFileName;

}