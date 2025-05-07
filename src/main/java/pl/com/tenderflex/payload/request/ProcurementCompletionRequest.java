package pl.com.tenderflex.payload.request;

public record ProcurementCompletionRequest(
        Integer contractId, 
        Integer rejectId) {
}