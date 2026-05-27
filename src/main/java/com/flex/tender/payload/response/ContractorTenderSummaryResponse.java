package com.flex.tender.payload.response;

public record ContractorTenderSummaryResponse(
        Integer id,
        String cpvCode,
        String fieldOfTheTender,
        String contractorName,
        String tenderStatusName,
        String tenderStatusLabel,
        String offerSubmissionDeadline,
        Integer offersAmount) {
}