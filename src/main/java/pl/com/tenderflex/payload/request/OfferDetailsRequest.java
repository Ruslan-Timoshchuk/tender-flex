package pl.com.tenderflex.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.com.tenderflex.model.Country;
import pl.com.tenderflex.model.Currency;

@Getter
@Setter
@NoArgsConstructor
public class OfferDetailsRequest {

    private Integer tenderId;
    private String organizationName;
    private String nationalRegistrationNumber;
    private Country country;
    private String city;
    private String firstName;
    private String lastName;
    private String phone;
    private Integer bidPrice;
    private Currency currency;

}