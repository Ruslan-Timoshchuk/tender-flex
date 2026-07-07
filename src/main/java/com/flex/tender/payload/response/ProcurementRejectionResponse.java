package com.flex.tender.payload.response;

public record ProcurementRejectionResponse(
        String tenderStatus, 
        String offerStatus) {
}