package pl.com.tenderflex.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Organization {

    private Integer id;
    private String name;
    private String nationalRegistrationNumber;
    private Country country;
    private String city;
    private ContactPerson contactPerson;

}