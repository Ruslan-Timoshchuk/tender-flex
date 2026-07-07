package com.flex.tender.payload.request;

/**
 * @author Ruslan Timoshchuk
 */
public record TenderRequest(
        Integer id, 
        CompanyProfileRequest companyProfile,
        Integer cpvId,
        String description, 
        String publication, 
        String offerSubmissionDeadline,
        ContractRequest contract,
        AwardDecisionRequest awardDecision,
        RejectDecisionRequest rejectDecision) {
}