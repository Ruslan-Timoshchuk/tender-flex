package pl.com.tenderflex.dto;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import pl.com.tenderflex.model.Country;
import pl.com.tenderflex.model.Currency;

public class ContractorOfferResponse {

    private Integer offerId;
    private String organizationNameByBidder;
    private String spvCode;
    private Integer price;
    private Currency currency;
    private Country country;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate receivedDate;
    private String status;

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public String getOrganizationNameByBidder() {
        return organizationNameByBidder;
    }

    public void setOrganizationNameByBidder(String organizationNameByBidder) {
        this.organizationNameByBidder = organizationNameByBidder;
    }

    public String getSpvCode() {
        return spvCode;
    }

    public void setSpvCode(String spvCode) {
        this.spvCode = spvCode;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}