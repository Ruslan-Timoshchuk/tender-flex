package pl.com.tenderflex.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
public class Contract {

    private Integer id;
    private File contractFile;
    private AwardDecision award;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate signedContractDeadline;
       
}