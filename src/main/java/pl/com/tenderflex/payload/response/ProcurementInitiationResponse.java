package pl.com.tenderflex.payload.response;

public record ProcurementInitiationResponse(
        Integer tenderId,
        Integer contractId,
        Integer awardDesisionId,
        Integer rejectDecisionId) {
}