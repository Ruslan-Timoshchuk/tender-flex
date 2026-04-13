package com.flex.tender.payload.response;

public record ContractorTenderSummaryResponse(
        Integer id,
        CpvResponse cpv,
        String contractorName,
        String tenderStatusLabel,
        String offerSubmissionDeadline,
        Integer offersAmount) {
}