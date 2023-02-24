package pl.com.tenderflex.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Organization {

    private Integer id;
    private String name;
    private String nationalRegistrationNumber;
    private Country country;
    private String city;
    private ContactPerson contactPerson;

}