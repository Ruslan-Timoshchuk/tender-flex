package pl.com.tenderflex.payload.response;

public record ProcurementCompletionResponse(
        Integer contractId,
        boolean hasSigned) {
}