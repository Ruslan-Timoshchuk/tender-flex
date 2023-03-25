package pl.com.tenderflex.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CPV {

    private final Integer id;
    private final String code;
    private final String description;
    
}