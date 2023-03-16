package pl.com.tenderflex.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Offer {

    private Integer id;
    private Integer bidderId;
    private Integer tenderId;
    private Organization organization;
    private Integer bidPrice;
    private Currency currency;
    private String contractorStatus;
    private String bidderStatus;
    private String documentUrl;

}