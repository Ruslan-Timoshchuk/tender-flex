package pl.com.tenderflex.companyprofile.payload;

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