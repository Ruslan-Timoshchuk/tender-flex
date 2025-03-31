package pl.com.tenderflex.payload.response;

public record TenderResponse(
        Integer id, 
        CompanyProfileResponse companyProfile,
        ProcedureResponse procedure, 
        CpvResponse cpv,
        String description, 
        String publicationDate, 
        String offerSubmissionDeadline,
        ContractResponse contract,
        AwardDecisionResponse awardDecision,
        RejectDecisionResponse rejectDecision,
        String status) {
}