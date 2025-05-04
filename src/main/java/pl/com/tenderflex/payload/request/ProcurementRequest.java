package pl.com.tenderflex.payload.request;

public record ProcurementRequest(
        TenderRequest tender,
        ContractRequest contract,
        AwardDecisionRequest awardDecision,
        RejectDecisionRequest rejectDecision) {
}