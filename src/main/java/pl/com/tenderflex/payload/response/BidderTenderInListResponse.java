package pl.com.tenderflex.payload.response;

import lombok.Data;

@Data
public class BidderTenderInListResponse {

    private Integer tenderId;
    private String cpvCode;
    private String cpvDescription;
    private String officialName;
    private String tenderStatus;
    private String deadline;
    private String offerStatus;
    
}