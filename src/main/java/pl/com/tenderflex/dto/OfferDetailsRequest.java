package pl.com.tenderflex.dto;

import pl.com.tenderflex.model.Country;
import pl.com.tenderflex.model.Currency;

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

    public Integer getTenderId() {
        return tenderId;
    }

    public void setTenderId(Integer tenderId) {
        this.tenderId = tenderId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getNationalRegistrationNumber() {
        return nationalRegistrationNumber;
    }

    public void setNationalRegistrationNumber(String nationalRegistrationNumber) {
        this.nationalRegistrationNumber = nationalRegistrationNumber;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Integer bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}