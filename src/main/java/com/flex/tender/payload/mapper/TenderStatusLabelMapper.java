package com.flex.tender.payload.mapper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import com.flex.tender.model.enumeration.ETenderStatus;

@Component
public class TenderStatusLabelMapper {

    @Named("viewLabel")
    public String toLabel(ETenderStatus status) {
        return switch (status) {
            case TENDER_IN_PROGRESS -> "Tender in progress";
            case TENDER_CLOSED -> "Tender closed";
            default -> throw new IllegalArgumentException("Unexpected value: " + status);
        };
    }

}