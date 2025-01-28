package pl.com.tenderflex.payload.request;

public record TenderRequest(
        Integer contractorId, 
        CompanyProfileRequest companyProfile,
        CpvRequest cpv,
        String description, 
        String publication, 
        String offerSubmissionDeadline,
        ContractRequest contract,
        AwardDecisionRequest award,
        RejectDecisionRequest reject) {
}