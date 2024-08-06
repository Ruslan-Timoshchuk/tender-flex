package pl.com.tenderflex.payload.response;

import lombok.Data;

@Data
public class OfferInListResponse {

    private Integer offerId;
    private String bidderOficialName;
    private String fieldOfTheTender;
    private Integer price;
    private String currency;
    private String country;
    private String date;
    private String status;

}