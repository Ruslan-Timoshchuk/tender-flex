package pl.com.tenderflex.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Offer {

    private Integer id;
    private Integer bidderId;
    private Tender tender;
    private CompanyProfile companyProfile;
    private OfferStatus globalStatus;
    private Integer bidPrice;
    private Currency currency;
    private LocalDate publication;
    private FileMetadata proposition;
 
}