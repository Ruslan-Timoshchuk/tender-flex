package pl.com.tenderflex.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class TenderStatus {

    private Integer id;
    private User user;
    private Contract contract;
    private EUserTenderStatus status;
    private LocalDate lastUpdated; 
    
}