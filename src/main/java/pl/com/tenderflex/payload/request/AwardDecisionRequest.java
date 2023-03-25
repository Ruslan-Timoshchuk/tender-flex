package pl.com.tenderflex.payload.request;

import lombok.Data;

@Data
public class AwardDecisionRequest {

    private final Integer offerId;
    private final String awardDecisionFileName;
    
}