package com.flex.tender.payload.response;

public record ContractorTenderSummaryResponse(
        Integer id,
        CpvResponse cpv,
        String contractorName,
        String tenderStatus,
        String offerSubmissionDeadline,
        Integer offersAmount) {
}