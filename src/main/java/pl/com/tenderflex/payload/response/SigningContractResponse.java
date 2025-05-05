package pl.com.tenderflex.payload.response;

public record SigningContractResponse(
        Integer contractId,
        boolean hasSigned) {
}