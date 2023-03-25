package pl.com.tenderflex.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Offer {

    private Integer id;
    private Integer bidderId;
    private Integer tenderId;
    private String fieldOfTheTender;
    private OfferStatus status;
    private Organization organization;
    private Integer bidPrice;
    private Currency currency;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate publicationDate;
    private String documentName;
    private String awardDecision;
    private String rejectDecision;

}