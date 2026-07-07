package com.flex.tender.model;

import java.time.LocalDate;
import com.flex.tender.model.embedded.Procedure;
import com.flex.tender.model.enumeration.ETenderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Tender {

    private Integer id;
    private User contractor;
    private CompanyProfile companyProfile;
    private Procedure procedure;
    private Cpv cpv;
    private String description;
    private ETenderStatus globalStatus;
    private LocalDate publicationDate; 
    private LocalDate offerSubmissionDeadline;
        
}