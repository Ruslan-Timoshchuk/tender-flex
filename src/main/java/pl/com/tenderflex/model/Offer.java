package pl.com.tenderflex.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Offer {

    private Integer id;
    private Integer bidderId;
    private Submission submission;
    private CompanyDetails bidderCompanyDetails;
    private ContactPerson contactPerson;
    private Integer bidPrice;
    private Currency currency;
    private LocalDate publicationDate;
    private File proposition;
 
}