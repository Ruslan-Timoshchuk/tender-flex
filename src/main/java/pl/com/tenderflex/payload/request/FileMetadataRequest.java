package pl.com.tenderflex.payload.request;

import lombok.Data;
import pl.com.tenderflex.model.enums.EFileCategory;
import pl.com.tenderflex.model.enums.EFileType;

@Data
public class FileMetadataRequest {

    private Integer bidId;
    private EFileType fileType;
    private EFileCategory fileCategory;
    
}