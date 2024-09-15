package pl.com.tenderflex.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TenderFile {

    private Integer id;
    private String name;
    private EFileType fileType;
    private String contentType;
    private String awsS3fileKey;
   
}