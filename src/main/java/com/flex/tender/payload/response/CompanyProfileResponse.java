package com.flex.tender.payload.response;

/**
 * @author Ruslan Tymoshchuk
 */

public record CompanyProfileResponse(
        Integer id,
        CountryResponse country,
        String city,
        String officialName, 
        String registrationNumber, 
        ContactPersonResponse contactPerson) {
}