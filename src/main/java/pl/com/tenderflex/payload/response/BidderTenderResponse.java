package pl.com.tenderflex.payload.response;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
public class BidderTenderResponse {

    private Integer tenderId;
    private String cpvCode;
    private String cpvDescription;
    private String organizationName;
    private String tenderStatus;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate deadline;
    private String offerStatus;
    
}