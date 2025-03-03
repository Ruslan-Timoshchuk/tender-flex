package pl.com.tenderflex.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileMetadata {

    private Integer id;
    private String name;
    private String contentType;
    private String awsS3fileKey;
   
}