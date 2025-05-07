package pl.com.tenderflex.payload.response;

public record ProcurementRejectionResponse(
        String tenderStatus, 
        String offerStatus) {
}