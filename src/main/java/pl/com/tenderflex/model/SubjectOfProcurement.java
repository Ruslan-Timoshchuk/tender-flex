package pl.com.tenderflex.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectOfProcurement {

    private Cpv cpv;
    private TypeOfTender type;
    private String description;
    private Integer minPrice;
    private Integer maxPrice;
    private Currency currency;
    
}