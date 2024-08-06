package pl.com.tenderflex.payload.iresponse;

public interface TenderDetails {

    Integer getTenderId();

    String getCompanyName();

    String getRegistrationNumber();

    String getCountry();

    String getCity();

    String getFirstName();

    String getLastName();

    String getPhone();

    String getProcedure();

    String getLanguage();

    String getCpvCode();

    String getCpvDescription();

    String getType();

    String getDescription();

    Integer getMaxTenderValue();

    Integer getMinTenderValue();

    String getCurrency();

    String getPublicationDate();

    String getOfferSubmissionDeadline();

    String getSignedContractSubmissionDeadline();

    String getContractFileName();

    String getAwardDecisionFileName();

    String getRejectDecisionFileName();

}