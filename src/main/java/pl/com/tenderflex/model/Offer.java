package pl.com.tenderflex.model;

import java.time.LocalDate;

public class Offer {

    private Integer id;
    private Integer bidderId;
    private Tender tender;
    private Organization organization;
    private Integer bidPrice;
    private Currency currency;
    private String documentName;
    private String contractorStatus;
    private LocalDate publicationDate;
    private String bidderStatus;

    public Integer getBidderId() {
        return bidderId;
    }

    public void setBidderId(Integer bidderId) {
        this.bidderId = bidderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tender getTender() {
        return tender;
    }

    public void setTender(Tender tender) {
        this.tender = tender;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
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

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getContractorStatus() {
        return contractorStatus;
    }

    public void setContractorStatus(String contractorStatus) {
        this.contractorStatus = contractorStatus;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getBidderStatus() {
        return bidderStatus;
    }

    public void setBidderStatus(String bidderStatus) {
        this.bidderStatus = bidderStatus;
    }
}