package pl.com.tenderflex.payload.request;

import lombok.Data;

@Data
public class TenderRequest {

    private Integer contractorId;
    private String officialName;
    private String registrationNumber;
    private Integer countryId;
    private String city;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Integer cpvId;
    private String description;
    private String publication;
    private String offerSubmissionDeadline;

}