package com.flex.tender.payload.response;

public record BidderTenderSummaryResponse(
        Integer id,
        String cpvCode,
        String fieldOfTheTender,
        String contractorName,
        String tenderStatusName,
        String tenderStatusLabel,
        String offerSubmissionDeadline,
        String offerStatusName,
        String offerStatusLabel) {
}