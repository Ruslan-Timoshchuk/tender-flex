package pl.com.tenderflex.payload.response;

public record OfferResponse(
        Integer id, 
        Integer tenderId,
        CompanyProfileResponse companyProfile,
        String status,
        Integer bidPrice,
        CurrencyResponse currency,
        String publication, 
        FileMetadataResponse proposition) {
}