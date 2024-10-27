package pl.com.tenderflex.model;

import java.time.LocalDate;
import lombok.Data;
import pl.com.tenderflex.model.enums.EOfferStatus;

@Data
public class ProcurementProtocol {

    private Integer id;
    private Tender tender;    
    private Offer offer;
    private EOfferStatus offerStatusContractor;  
    private AwardDecision award;
    private LocalDate createdDate;
    private LocalDate lastUpdated;
   
}