package pl.com.tenderflex.contract.payload;

import pl.com.tenderflex.currency.payload.CurrencyResponse;
import pl.com.tenderflex.payload.iresponse.response.FileMetadataResponse;

public record ContractResponse(
        Integer id, 
        ContractTypeResponse contractType, 
        Integer minPrice, 
        Integer maxPrice,
        CurrencyResponse currency, 
        FileMetadataResponse fileMetadata, 
        String signedDeadline) {
}