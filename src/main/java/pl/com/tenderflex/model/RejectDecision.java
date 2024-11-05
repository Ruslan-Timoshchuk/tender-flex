package pl.com.tenderflex.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class RejectDecision {

    private Integer id;
    private Tender tender;
    private File rejectFile;
    private LocalDate createdDate;
    private LocalDate lastUpdated;
    
}