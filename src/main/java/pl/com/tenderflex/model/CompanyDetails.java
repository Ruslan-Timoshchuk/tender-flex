package pl.com.tenderflex.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyDetails {

    private String officialName;
    private String registrationNumber;
    private Country country;
    private String city;

}