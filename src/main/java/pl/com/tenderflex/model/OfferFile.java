package pl.com.tenderflex.model;

import lombok.Builder;
import lombok.Data;
import pl.com.tenderflex.model.enums.EFileType;

@Data
@Builder
public class OfferFile {

    private Integer id;
    private String name;
    private EFileType type;
    private String contentType;
    private String awsS3fileKey;
   
}