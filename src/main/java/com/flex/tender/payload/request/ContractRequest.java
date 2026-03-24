package com.flex.tender.payload.request;

public record ContractRequest(
        ContractTypeRequest contractType, 
        Integer minPrice, 
        Integer maxPrice,
        CurrencyRequest currency, 
        String signedDeadline,
        FileMetadataRequest fileMetadata) {
}