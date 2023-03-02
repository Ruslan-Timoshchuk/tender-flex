package pl.com.tenderflex.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.com.tenderflex.model.TenderType;

@Getter
@Setter
@NoArgsConstructor
public class TenderDetailsRequest {

    private String organizationName;
    private String nationalRegistrationNumber;
    private Integer countryId;
    private String city;
    private String firstName;
    private String lastName;
    private String phone;
    private String cpvCode;
    private TenderType type;
    private String details;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer currencyId;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate publication;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate deadline;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate deadlineForSignedContract;

}