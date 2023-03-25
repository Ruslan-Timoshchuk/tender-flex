package pl.com.tenderflex.payload.request;

import lombok.Data;

@Data
public class RejectDecisionRequest {
    
    private final Integer offerId;
    private final String rejectDecisionFileName;
    
}