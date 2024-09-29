package pl.com.tenderflex.payload.request;

import lombok.Data;

@Data
public class TenderDetailsRequest {

    private Integer userId;
    private String officialName;
    private String registrationNumber;
    private Integer countryId;
    private String city;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Integer cpvId;
    private Integer typeOfTenderId;
    private String description;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer currencyId;
    private String publication;
    private String offerSubmissionDeadline;
    private String signedContractDeadline;

}