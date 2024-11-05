package pl.com.tenderflex.payload.iresponse.response;

import lombok.Data;

@Data
public class AwardResponse {

    private Integer id;
    private FileMetadataResponse fileMetadata;
    
}