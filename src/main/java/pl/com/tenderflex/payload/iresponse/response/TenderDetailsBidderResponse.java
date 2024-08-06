package pl.com.tenderflex.payload.iresponse.response;

import lombok.Data;
import pl.com.tenderflex.payload.iresponse.TenderDetails;

@Data
public class TenderDetailsBidderResponse implements TenderDetails {
    
    private Integer tenderId;
    private String companyName;
    private String registrationNumber;
    private String country;
    private String city;
    private String firstName;
    private String lastName;
    private String phone;
    private String procedure;
    private String language;
    private String cpvCode;
    private String cpvDescription;
    private String type;
    private String description;
    private Integer maxTenderValue;
    private Integer minTenderValue;
    private String currency;
    private String publicationDate;
    private String offerSubmissionDeadline;
    private String signedContractSubmissionDeadline;
    private String contractFileName;
    private String awardDecisionFileName;
    private String rejectDecisionFileName;
    
}