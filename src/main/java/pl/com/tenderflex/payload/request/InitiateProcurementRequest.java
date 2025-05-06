package pl.com.tenderflex.payload.request;

public record InitiateProcurementRequest(
        TenderRequest tender,
        ContractRequest contract,
        AwardDecisionRequest awardDecision,
        RejectDecisionRequest rejectDecision) {
}