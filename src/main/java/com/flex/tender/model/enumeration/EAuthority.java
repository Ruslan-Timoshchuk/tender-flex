package com.flex.tender.model.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EAuthority {

    BIDDER("Bidder"), 
    CONTRACTOR("Contractor"), 
    ADMINISTRATOR("Administrator");

    private final String label;

}