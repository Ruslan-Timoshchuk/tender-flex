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
    private Tender tender;
    private Organization organization;
    private Integer bidPrice;
    private Currency currency;
    private String contractorStatus;
    private String bidderStatus;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate publicationDate;
    private String documentUrl;

}