package com.flex.tender.payload.request;

public record ProcurementCompletionRequest(
        Integer contractId, 
        Integer rejectId) {
}