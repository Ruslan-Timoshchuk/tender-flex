package pl.com.tenderflex.payload.request;

import lombok.Data;

@Data
public class DecisionRequest {

    private final Integer tenderId;
    private final Integer offerId;
    private final String decision;
    
}