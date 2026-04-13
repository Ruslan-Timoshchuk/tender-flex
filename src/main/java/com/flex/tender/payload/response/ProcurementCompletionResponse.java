package com.flex.tender.payload.response;

public record ProcurementCompletionResponse(
        Integer contractId,
        String status,
        String offerStatus) {
}