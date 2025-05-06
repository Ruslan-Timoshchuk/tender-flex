package pl.com.tenderflex.payload.request;

public record CompleteProcurementRequest(
        Integer contractId, 
        Integer rejectId) {
}