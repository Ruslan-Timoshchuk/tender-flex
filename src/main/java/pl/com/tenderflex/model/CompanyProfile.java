package pl.com.tenderflex.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyProfile {

    private Integer id;
    private String officialName;
    private String registrationNumber;
    private Country country;
    private String city;
    private ContactPerson contactPerson;
    
}