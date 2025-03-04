package pl.com.tenderflex.payload.request;

public record TenderRequest(
        Integer id,
        Integer contractorId, 
        CompanyProfileRequest companyProfile,
        CpvRequest cpv,
        String description, 
        String publication, 
        String offerSubmissionDeadline,
        ContractRequest contract,
        AwardDecisionRequest awardDecision,
        RejectDecisionRequest rejectDecision) {
}