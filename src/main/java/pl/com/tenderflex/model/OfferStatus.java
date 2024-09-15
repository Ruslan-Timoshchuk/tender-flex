package pl.com.tenderflex.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class OfferStatus {

    private Integer id;
    private User user;
    private Offer offer;
    private EUserOfferStatus status;
    private LocalDate lastUpdated;
    
}