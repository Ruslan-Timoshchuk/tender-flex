package pl.com.tenderflex.payload.response;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
public class OfferResponse {

    private Integer offerId;
    private String organizationNameByBidder;
    private String fieldOfTheTender;
    private Integer price;
    private String country;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;
    private String contractorSt;
    private String bidderSt;

}