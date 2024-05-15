package pl.com.tenderflex.payload.request;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class TenderDetailsRequest {

    private String company;
    private String registrationNumber;
    private Integer countryId;
    private String city;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Integer cpvId;
    private Integer typeOfTenderId;
    private String details;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer currencyId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publication;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadlineForSignedContract;
    private String contractFileName;
    private String awardDecisionFileName;
    private String rejectDecisionFileName;

}