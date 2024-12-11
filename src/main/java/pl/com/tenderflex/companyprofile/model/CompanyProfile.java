package pl.com.tenderflex.companyprofile.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyProfile {

    private Integer id;
    private Country country;
    private String city;
    private String officialName;
    private String registrationNumber;
    private ContactPerson contactPerson;
    
}