package pl.com.tenderflex.payload.request;

import lombok.Data;

@Data
public class RejectRequest {
    
    private final Integer tenderId;
    private final String rejectFileId;
    
}