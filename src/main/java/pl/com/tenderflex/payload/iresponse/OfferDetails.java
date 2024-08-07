package pl.com.tenderflex.payload.iresponse;

public interface OfferDetails {

    Integer getOfferId();

    String getBidderCompanyName();

    String getRegistrationNumber();

    String getCountry();

    String getCity();

    String getFirstName();

    String getLastName();

    String getPhone();

    String getBidPrice();

    String getCurrency();

    String getDocumentName();

    String getStatus();

}