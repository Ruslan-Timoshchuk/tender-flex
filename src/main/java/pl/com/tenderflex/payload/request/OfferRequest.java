package pl.com.tenderflex.payload.request;

public record OfferRequest(
        Integer bidderId,
        TenderRequest tender,      
        CompanyProfileRequest companyProfile,
        Integer bidPrice, 
        CurrencyRequest currency,
        String publication, 
        FileMetadataRequest proposition) {
}