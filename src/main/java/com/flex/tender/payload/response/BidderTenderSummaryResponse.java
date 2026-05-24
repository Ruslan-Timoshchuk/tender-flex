package com.flex.tender.payload.response;

public record BidderTenderSummaryResponse(
        Integer id,
        CpvResponse cpv,
        String contractorName,
        String tenderStatusName,
        String tenderStatusLabel,
        String offerSubmissionDeadline,
        String offerStatusLabel) {
}