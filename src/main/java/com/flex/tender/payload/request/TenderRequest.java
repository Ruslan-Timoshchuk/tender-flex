package com.flex.tender.payload.request;

public record TenderRequest(
        Integer id, 
        CompanyProfileRequest companyProfile,
        Integer cpvId,
        String description, 
        String publication, 
        String offerSubmissionDeadline) {
}