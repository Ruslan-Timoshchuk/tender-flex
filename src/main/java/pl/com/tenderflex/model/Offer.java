package pl.com.tenderflex.model;

import java.time.LocalDate;
import java.util.Set;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Offer {

    private Integer id;
    private Integer bidderId;
    private CompanyDetails bidderCompanyDetails;
    private ContactPerson contactPerson;
    private Integer bidPrice;
    private Currency currency;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate publicationDate;
    private Contract contract;
    private OfferFile proposition;
    private Set<OfferStatus>userOfferStatuses;

}