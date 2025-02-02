package pl.com.tenderflex.payload.request;

import lombok.Data;

@Data
public class OfferDetailsRequest {

    private Integer tenderId;
    private String company;
    private String registrationNumber;
    private Integer countryId;
    private String city;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Integer bidPrice;
    private Integer currencyId;
    private String documentName;
    
}