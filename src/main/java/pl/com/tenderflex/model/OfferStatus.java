package pl.com.tenderflex.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferStatus {

    private final Integer id;
    private final String contractor;
    private final String bidder;
    
}