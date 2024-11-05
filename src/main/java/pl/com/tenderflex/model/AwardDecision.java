package pl.com.tenderflex.model;

import lombok.Data;

@Data
public class AwardDecision {

    private Integer id;
    private Tender tender;
    private File awardFile;
   
}