package pl.com.tenderflex.tender.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Cpv {

    private Integer id;
    private String code;
    private String description;
    
}