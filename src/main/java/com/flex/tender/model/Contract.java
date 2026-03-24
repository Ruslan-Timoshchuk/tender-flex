package com.flex.tender.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.flex.tender.model.enumeration.EContractStatus;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Contract {

    private Integer id;
    private Tender tender;
    private Offer offer;
    private ContractType contractType;
    private Integer minPrice;
    private Integer maxPrice;
    private Currency currency;
    private FileMetadata fileMetadata;
    private EContractStatus globalStatus;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @FutureOrPresent(message = "The contract signing deadline must be today or a future date.")
    private LocalDate signedDeadline;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate signedDate;

}