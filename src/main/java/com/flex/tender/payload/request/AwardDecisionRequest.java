package com.flex.tender.payload.request;

public record AwardDecisionRequest(
        Integer id,
        Integer tenderId,
        Integer fileMetadataId) {
}