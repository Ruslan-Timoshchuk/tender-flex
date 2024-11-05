package pl.com.tenderflex.payload.iresponse.response;

import lombok.Data;

@Data
public class TenderResponse {
    
    private Integer id;
    private String companyName;
    private String registrationNumber;
    private CountryResponse country;
    private String city;
    private String firstName;
    private String lastName;
    private String phone;
    private String procedure;
    private String language;
    private CpvResponse cpv;
    private String description;
    private String publicationDate;
    private String offerSubmissionDeadline;
    private ContractResponse contract;
    private AwardResponse award;
    private RejectResponse reject;
        
}