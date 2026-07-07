package com.flex.tender.model.embedded;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContactPerson {

    private String firstName;
    private String lastName;
    private String phoneNumber;

}