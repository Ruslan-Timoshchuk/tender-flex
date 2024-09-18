package pl.com.tenderflex.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class OfferStatus {

    private Integer id;
    private Integer userId;
    private Integer offerId;
    private EUserOfferStatus status;
    private LocalDate lastUpdated;
    
}