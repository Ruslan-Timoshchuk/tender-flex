package com.flex.tender.payload.response;

/**
 * @author Ruslan Timoshchuk
 */
public record ContractResponse(
        Integer id, 
        ContractTypeResponse contractType, 
        String status,
        Integer minPrice, 
        Integer maxPrice,
        CurrencyResponse currency, 
        FileMetadataResponse fileMetadata, 
        String signedDeadline) {
}