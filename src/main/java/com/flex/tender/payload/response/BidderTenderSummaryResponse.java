package com.flex.tender.payload.response;

public record BidderTenderSummaryResponse(
        Integer id,
        CpvResponse cpv,
        String contractorName,
        String tenderStatus,
        String offerSubmissionDeadline,
        Integer offerStatus) {
}