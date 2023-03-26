package pl.com.tenderflex.payload.response;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;
import pl.com.tenderflex.model.TenderType;

@Data
public class BidderTenderDetailsResponse {

    private Integer tenderId;
    private String organizationName;
    private String nationalRegistrationNumber;
    private String tenderStatus;
    private String offerStatus;
    private String country;
    private String city;
    private String firstName;
    private String lastName;
    private String phone;
    private String procedure;
    private String language;
    private String cpvCode;
    private String cpvDescription;
    private TenderType type;
    private String description;
    private Integer minTenderValue;
    private String currency;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate tenderPublicationDate;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate deadlineForOfferSubmission;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate deadlineForSignedContractSubmission;
    private String contractFileName;
    
}