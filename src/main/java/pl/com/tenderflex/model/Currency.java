package pl.com.tenderflex.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Currency {

    private Integer id;
    private String type;
    
}