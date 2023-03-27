package pl.com.tenderflex.payload.response;

import lombok.Data;

@Data
public class DecisionResponse {

    private final Integer offerId;
    private final String bidderSt;
    
}