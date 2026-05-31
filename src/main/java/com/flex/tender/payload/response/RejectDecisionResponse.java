package com.flex.tender.payload.response;

/**
 * @author Ruslan Timoshchuk
 */
public record RejectDecisionResponse(
        Integer id, 
        FileMetadataResponse fileMetadata) {
}