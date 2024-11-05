package pl.com.tenderflex.payload.request;

import lombok.Data;

@Data
public class AwardRequest {

    private final Integer tenderId;
    private final Integer awardFileId;
    
}