package pl.com.tenderflex.payload.request;

public record SigningContractRequest(
        Integer contractId, 
        Integer rejectId) {
}