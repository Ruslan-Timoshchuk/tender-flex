package pl.com.tenderflex.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class AwardDecision {

    private Integer id;
    private File awardFile;
    private LocalDate createdDate;
    private LocalDate lastUpdated;
   
}