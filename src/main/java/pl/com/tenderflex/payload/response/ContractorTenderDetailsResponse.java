package pl.com.tenderflex.payload.response;

import lombok.Data;
import pl.com.tenderflex.model.TenderType;

@Data
public class ContractorTenderDetailsResponse {
    
    private Integer tenderId;
    private String organizationName;
    private String nationalRegistrationNumber;
    private String country;
    private String city;
    private String firstName;
    private String lastName;
    private String phone;
    private String procedure;
    private String language;
    private String cpvCode;
    private TenderType type;
    private String description;
    private Integer minTenderValue;
    private Integer maxTenderValue;
    private String currency;
    private String tenderPublicationDate;
    private String deadlineForOfferSubmission;
    private String deadlineForSignedContractSubmission;
    private String contractFileUrl;
    private String awardDecisionFileUrl;
    private String rejectDecisionFileUrl;
    
}