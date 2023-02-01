package pl.com.tenderflex.model;

public class Offer {

    private Integer id;
    private Integer bidderId;
    private Integer tenderId;
    private Organization organization;
    private Integer bidPrice;
    private Currency currency;
    private String documentName;

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

    public Integer getTenderId() {
        return tenderId;
    }

    public void setTenderId(Integer tenderId) {
        this.tenderId = tenderId;
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
}