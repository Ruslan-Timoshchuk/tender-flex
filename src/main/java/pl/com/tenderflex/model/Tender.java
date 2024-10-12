package pl.com.tenderflex.model;

import java.time.LocalDate;
import java.util.Set;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Builder;
import lombok.Data;
import pl.com.tenderflex.model.enums.ETenderStatus;

@Data
@Builder
public class Tender {

    private Integer id;
    private Integer contractorId;
    private CompanyDetails contractorCompanyDetails;
    private ContactPerson contactPerson;
    private Procedure procedure;
    private SubjectOfProcurement subjectOfProcurement;
    private ETenderStatus status; 
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate publication; 
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate offerSubmissionDeadline;
    private Contract contract;
    private RejectDecision reject;
    private Set<Offer>offers;

}