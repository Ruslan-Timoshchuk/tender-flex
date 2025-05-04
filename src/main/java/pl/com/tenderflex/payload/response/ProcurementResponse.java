package pl.com.tenderflex.payload.response;

public record ProcurementResponse(
        Integer tenderId,
        Integer contractId,
        Integer awardDesisionId,
        Integer rejectDecisionId) {
}