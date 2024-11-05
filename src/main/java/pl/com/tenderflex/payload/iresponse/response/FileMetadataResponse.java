package pl.com.tenderflex.payload.iresponse.response;

import lombok.Data;

@Data
public class FileMetadataResponse {

    private Integer id;
    private String name;
    private String contentType;
    private String awsS3fileKey;
    
}