package pl.com.tenderflex.model;

import java.time.LocalDate;
import javax.validation.constraints.FutureOrPresent;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
public class Contract {

    private Integer id;
    private Tender tender;
    private Offer offer;
    private ContractType contractType;
    private Integer minPrice;
    private Integer maxPrice;
    private Currency currency;
    private File contractFile;
    
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @FutureOrPresent(message = "The contract signing deadline must be today or a future date.")
    private LocalDate signedDeadline;
    
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate signedDate;
       
}