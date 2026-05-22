package com.flex.tender.payload.request;

/**
 * @author Ruslan Timoshchuk
 */
public record RejectDecisionRequest(
        Integer id, 
        Integer tenderId, 
        Integer fileMetadataId) {
}