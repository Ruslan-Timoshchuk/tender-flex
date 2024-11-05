package pl.com.tenderflex.payload.iresponse.response;

import lombok.Data;

@Data
public class RejectResponse {

    private Integer id;
    private FileMetadataResponse fileMetadata;
    
}