package pl.com.tenderflex.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TenderStatus {

    private final Integer id;
    private final String status;
    
}
