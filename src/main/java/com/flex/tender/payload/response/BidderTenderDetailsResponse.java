package com.flex.tender.payload.response;

/**
 * @author Ruslan Timoshchuk
 */
public record BidderTenderDetailsResponse(
        Integer id, 
        CompanyProfileResponse companyProfile,
        ProcedureResponse procedure,
        CpvResponse cpv,
        String description, 
        String publicationDate, 
        String offerSubmissionDeadline,
        ContractResponse contract) {
}