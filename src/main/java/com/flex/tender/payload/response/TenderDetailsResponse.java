package com.flex.tender.payload.response;

/**
 * @author Ruslan Timoshchuk
 */
public record TenderDetailsResponse(
        Integer id, 
        CompanyProfileResponse companyProfile,
        ProcedureResponse procedure,
        CpvResponse cpv,
        String description, 
        String publicationDate, 
        String offerSubmissionDeadline,
        ContractResponse contract,
        AwardDecisionResponse awardDecision,
        RejectDecisionResponse rejectDecision) {
}