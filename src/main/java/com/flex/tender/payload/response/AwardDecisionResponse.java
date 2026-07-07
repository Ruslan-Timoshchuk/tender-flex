package com.flex.tender.payload.response;

/**
 * @author Ruslan Timoshchuk
 */
public record AwardDecisionResponse(
        Integer id, 
        FileMetadataResponse fileMetadata) {
}