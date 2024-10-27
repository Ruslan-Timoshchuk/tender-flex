package pl.com.tenderflex.model;

import java.time.LocalDate;
import lombok.Data;
import pl.com.tenderflex.model.enums.EOfferStatus;
import pl.com.tenderflex.model.enums.ETenderStatus;

@Data
public class Submission {

    private Integer id;
    private Integer bidderId;
    
    private Tender tender;
    private ETenderStatus tenderStatus;
    
    private Offer offer;
    private EOfferStatus offerStatus;
    
    private LocalDate submittedDate;
    private LocalDate lastUpdated;
    private RejectDecision reject;

}