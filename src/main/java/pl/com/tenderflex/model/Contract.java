package pl.com.tenderflex.model;

import java.time.LocalDate;
import java.util.Set;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Contract {

    private Integer id;
    private Set<Offer>offers;
    private EContractStatus status;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate offerSubmissionDeadline;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate signedContractDeadline;
    
}