package pl.com.tenderflex.contract.payload;

public record ContractRequest(
        Integer tenderId, 
        Integer contractTypeId, 
        Integer minPrice, 
        Integer maxPrice,
        Integer currencyId, 
        Integer fileId, 
        String signedDeadline) {
}