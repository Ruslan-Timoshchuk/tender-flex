package pl.com.tenderflex.payload.response;

public record TenderResponse(
        Integer id, 
        Integer contractId,
        CompanyProfileResponse companyProfile,
        ProcedureResponse procedure, 
        CpvResponse cpv,
        String description, 
        String publicationDate, 
        String offerSubmissionDeadline,
        AwardDecisionResponse awardDecision,
        RejectDecisionResponse rejectDecision,
        String status) {
}