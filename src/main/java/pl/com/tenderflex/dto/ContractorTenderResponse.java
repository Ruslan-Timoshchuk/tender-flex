package pl.com.tenderflex.dto;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class ContractorTenderResponse {

    private Integer tenderId;
    private String cpvCode;
    private String organizationName;
    private String status;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate deadline;
    private Integer offersAmount;

    public Integer getTenderId() {
        return tenderId;
    }

    public void setTenderId(Integer tenderId) {
        this.tenderId = tenderId;
    }

    public String getCpvCode() {
        return cpvCode;
    }

    public void setCpvCode(String cpvCode) {
        this.cpvCode = cpvCode;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Integer getOffersAmount() {
        return offersAmount;
    }

    public void setOffersAmount(Integer offersAmount) {
        this.offersAmount = offersAmount;
    }
}