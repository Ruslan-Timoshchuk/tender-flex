package pl.com.tenderflex.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tender {

    private Integer id;
    private Integer contractorId;
    private CompanyDetails contractor;
    private ContactPerson contactPerson;
    private CPV cpv;
    private TypeOfTender type;
    private TenderStatus status;
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
    private String contractFileName;
    private String awardDecisionFileName;
    private String rejectDecisionFileName;
    private Integer offersAmount;

}