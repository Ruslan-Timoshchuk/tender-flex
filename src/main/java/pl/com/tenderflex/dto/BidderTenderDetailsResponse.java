package pl.com.tenderflex.dto;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import pl.com.tenderflex.model.Country;
import pl.com.tenderflex.model.Currency;
import pl.com.tenderflex.model.TenderType;

public class BidderTenderDetailsResponse {

    private Integer tenderId;
    private String organizationName;
    private String nationalRegistrationNumber;
    private Country country;
    private String city;
    private String firstName;
    private String lastName;
    private String phone;
    private String procedure;
    private String language;
    private String cpvCode;
    private TenderType type;
    private String description;
    private Integer minTenderValue;
    private Currency currency;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate tenderPublicationDate;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate deadlineForOfferSubmission;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate deadlineForSignedContractSubmission;
    private String contractFileNameByContractor;

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

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCpvCode() {
        return cpvCode;
    }

    public void setCpvCode(String cpvCode) {
        this.cpvCode = cpvCode;
    }

    public TenderType getType() {
        return type;
    }

    public void setType(TenderType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMinTenderValue() {
        return minTenderValue;
    }

    public void setMinTenderValue(Integer minTenderValue) {
        this.minTenderValue = minTenderValue;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public LocalDate getTenderPublicationDate() {
        return tenderPublicationDate;
    }

    public void setTenderPublicationDate(LocalDate tenderPublicationDate) {
        this.tenderPublicationDate = tenderPublicationDate;
    }

    public LocalDate getDeadlineForOfferSubmission() {
        return deadlineForOfferSubmission;
    }

    public void setDeadlineForOfferSubmission(LocalDate deadlineForOfferSubmission) {
        this.deadlineForOfferSubmission = deadlineForOfferSubmission;
    }

    public LocalDate getDeadlineForSignedContractSubmission() {
        return deadlineForSignedContractSubmission;
    }

    public void setDeadlineForSignedContractSubmission(LocalDate deadlineForSignedContractSubmission) {
        this.deadlineForSignedContractSubmission = deadlineForSignedContractSubmission;
    }

    public String getContractFileNameByContractor() {
        return contractFileNameByContractor;
    }

    public void setContractFileNameByContractor(String contractFileNameByContractor) {
        this.contractFileNameByContractor = contractFileNameByContractor;
    }
}