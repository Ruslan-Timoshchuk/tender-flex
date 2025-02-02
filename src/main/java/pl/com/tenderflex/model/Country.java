package pl.com.tenderflex.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Country {

    private Integer id;
    private String name;
    private String isoCode;
    private String phoneCode;
    
}