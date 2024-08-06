package pl.com.tenderflex.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tender {

    private Integer id;
    private User contractor;
    private CompanyDetails contractorCompanyDetails;
    private ContactPerson contactPerson;
    private CPV cpv;
    private TypeOfTender type;
    private ETenderStatus status;
    private String details;
    private Integer maxPrice;
    private Integer minPrice;
    private Currency currency;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate publication;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate deadline;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate signedContractDeadline;
    private String contractFileName;
    private String awardDecisionFileName;
    private String rejectDecisionFileName;
   
}