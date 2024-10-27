package pl.com.tenderflex.model;

import java.time.LocalDate;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import pl.com.tenderflex.model.enums.ETenderStatus;

@Data
@Builder
public class Tender {

    private Integer id;
    private Integer contractorId;
    private Procedure procedure;
    private CompanyDetails contractorCompanyDetails;
    private ContactPerson contactPerson;
    private SubjectOfProcurement subjectOfProcurement;
    private ETenderStatus status; 
    private LocalDate publicationDate; 
    private LocalDate offerSubmissionDeadline;
    private Contract contract;
    private Set<ProcurementProtocol> submittedProtocols;
    private Set<Submission> submissions;
        
}