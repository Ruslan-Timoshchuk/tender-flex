package pl.com.tenderflex.payload.request;

/**
 * Request DTO for creating or updating a company profile.
 * <p>
 * This DTO is used to transfer company profile data between the client and server.
 * </p>
 * 
 * <ul>
 *   <li><b>officialName</b>: The official name of the company.</li>
 *   <li><b>registrationNumber</b>: The unique registration number of the company.</li>
 *   <li><b>countryId</b>: The identifier of the country associated with the company.</li>
 *   <li><b>city</b>: The city where the company is located.</li>
 *   <li><b>firstName</b>: The first name of the company’s contact person.</li>
 *   <li><b>lastName</b>: The last name of the company’s contact person.</li>
 *   <li><b>phoneNumber</b>: The phone number of the company’s contact person.</li>
 * </ul>
 * 
 * @author Ruslan Tymoshchuk
 */

public record CompanyProfileRequest(
        String officialName, 
        String registrationNumber, 
        CountryRequest country, 
        String city,
        ContactPersonRequest contactPerson) {
}