package com.flex.tender.model.enumeration;

public enum EAuthority {

    BIDDER("Bidder"), 
    CONTRACTOR("Contractor"), 
    ADMINISTRATOR("Administrator");

    private final String label;

    EAuthority(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}