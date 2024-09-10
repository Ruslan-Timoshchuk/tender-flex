package pl.com.tenderflex.model;

import java.time.LocalDate;
import java.util.Set;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tender {

    private Integer id;
    private User contractor;
    private CompanyDetails contractorCompanyDetails;
    private ContactPerson contactPerson;
    private Cpv cpv;
    private TypeOfTender type;
    private String details;
    private Integer maxPrice;
    private Integer minPrice;
    private Currency currency;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate publication;
    private Contract contract;
    private Set<TenderFile>files;
    
}