package pl.com.tenderflex.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Builder;
import lombok.Data;
import pl.com.tenderflex.model.enums.EOfferStatus;

@Data
@Builder
public class Offer {

    private Integer id;
    private Integer bidderId;
    private Tender tender;
    private CompanyDetails bidderCompanyDetails;
    private ContactPerson contactPerson;
    private Integer bidPrice;
    private Currency currency;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate publicationDate;
    private File proposition;
    private Contract contract;
    private RejectDecision reject;
    private EOfferStatus contractorStatus;
    private EOfferStatus bidderStatus;
     
}