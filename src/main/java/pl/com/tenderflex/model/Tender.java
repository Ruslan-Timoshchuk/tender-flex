package pl.com.tenderflex.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class Tender {

    private Integer id;
    private User contractor;
    private Organization organization;
    private String cpvCode;
    private TenderType type;
    private String details;
    private Integer minPrice;
    private Integer maxPrice;
    private Currency currency;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private final LocalDate publication;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private final LocalDate deadline;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate deadlineForSignedContract;
    private String contractFileName;
    private String awardDecisionFileName;
    private String rejectDecisionFileName;

    public Tender() {
        this.publication = LocalDate.now();
        this.deadline = LocalDate.now().plusDays(1);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getContractor() {
        return contractor;
    }

    public void setContractor(User contractor) {
        this.contractor = contractor;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public LocalDate getDeadlineForSignedContract() {
        return deadlineForSignedContract;
    }

    public void setDeadlineForSignedContract(LocalDate deadlineForSignedContract) {
        this.deadlineForSignedContract = deadlineForSignedContract;
    }

    public String getContractFileName() {
        return contractFileName;
    }

    public void setContractFileName(String contractFileName) {
        this.contractFileName = contractFileName;
    }

    public String getAwardDecisionFileName() {
        return awardDecisionFileName;
    }

    public void setAwardDecisionFileName(String awardDecisionFileName) {
        this.awardDecisionFileName = awardDecisionFileName;
    }

    public String getRejectDecisionFileName() {
        return rejectDecisionFileName;
    }

    public void setRejectDecisionFileName(String rejectDecisionFileName) {
        this.rejectDecisionFileName = rejectDecisionFileName;
    }

    public LocalDate getPublication() {
        return publication;
    }

    public LocalDate getDeadline() {
        return deadline;
    }
}