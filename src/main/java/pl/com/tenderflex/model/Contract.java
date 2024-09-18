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
    private Tender tender;
    private Set<Offer>offers;
    private EContractStatus status;
    private EProcedure procedureType;
    private ELanguage language;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate offerSubmissionDeadline;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate signedContractDeadline;
    
}