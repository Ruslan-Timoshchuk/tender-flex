package pl.com.tenderflex.companyprofile.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactPerson {

    private String firstName;
    private String lastName;
    private String phoneNumber;

}