package pl.com.tenderflex.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class Tender {

    private Integer id;
    private Integer contractorId;
    private Organization organization;
    private String cpvCode;
    private TenderType type;
    private String details;
    private Integer minPrice;
    private Integer maxPrice;
    private Currency currency;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate publication;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate deadline;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate deadlineForSignedContract;
    private String status;
    private String contractFileName;
    private String awardDecisionFileName;
    private String rejectDecisionFileName;

    public Tender() {
    }

    public Tender(Organization organization, LocalDate publication) {
        this.organization = organization;
        this.publication = publication;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContractorId() {
        return contractorId;
    }

    public void setContractorId(Integer contractorId) {
        this.contractorId = contractorId;
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

    public LocalDate getPublication() {
        return publication;
    }

    public void setPublication(LocalDate publication) {
        this.publication = publication;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public LocalDate getDeadlineForSignedContract() {
        return deadlineForSignedContract;
    }

    public void setDeadlineForSignedContract(LocalDate deadlineForSignedContract) {
        this.deadlineForSignedContract = deadlineForSignedContract;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}