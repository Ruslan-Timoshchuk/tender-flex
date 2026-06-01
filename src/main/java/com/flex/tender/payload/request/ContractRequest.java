package com.flex.tender.payload.request;

public record ContractRequest(
        Integer id,
        Integer tenderId,
        Integer offerId,
        Integer contractTypeId, 
        Integer minPrice, 
        Integer maxPrice,
        Integer currencyId, 
        String signedDeadline,
        Integer fileMetadataId) {
}