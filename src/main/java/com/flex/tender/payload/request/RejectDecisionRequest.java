package com.flex.tender.payload.request;

public record RejectDecisionRequest(
        Integer id, 
        Integer tenderId, 
        Integer filemetadataId) {
}