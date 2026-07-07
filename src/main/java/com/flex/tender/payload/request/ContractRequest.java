package com.flex.tender.payload.request;

/**
 * @author Ruslan Timoshchuk
 */
public record ContractRequest(
        Integer contractTypeId, 
        Integer minPrice, 
        Integer maxPrice,
        Integer currencyId, 
        String signedDeadline,
        Integer fileMetadataId) {
}