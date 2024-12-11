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
        String firstName, 
        String lastName, 
        String phoneNumber) {
}