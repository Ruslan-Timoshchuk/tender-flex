package com.flex.tender.payload.request;

public record TenderRequest(
        Integer id,
        Integer contractorId, 
        CompanyProfileRequest companyProfile,
        CpvRequest cpv,
        String description, 
        String publication, 
        String offerSubmissionDeadline) {
}