package com.flex.tender.model;

import com.flex.tender.model.embedded.ContactPerson;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CompanyProfile {

    private Integer id;
    private String officialName;
    private String registrationNumber;
    private Country country;
    private String city;
    private ContactPerson contactPerson;
    
}