package pl.com.tenderflex.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import pl.com.tenderflex.model.enums.ETenderStatus;

@Data
@Builder
public class Tender {

    private Integer id;
    private Integer contractorId;
    private CompanyProfile companyProfile;
    private Procedure procedure;
    private Cpv cpv;
    private String description;
    private ETenderStatus globalStatus; 
    private Contract contract;
    private AwardDecision award;
    private RejectDecision reject;
    private LocalDate publicationDate; 
    private LocalDate offerSubmissionDeadline;
        
}