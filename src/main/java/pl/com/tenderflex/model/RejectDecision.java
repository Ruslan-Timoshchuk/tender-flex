package pl.com.tenderflex.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RejectDecision {

    private Integer id;
    private Tender tender;
    private FileMetadata fileMetadata;
    
}