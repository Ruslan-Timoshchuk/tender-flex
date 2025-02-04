package pl.com.tenderflex.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContractType {

    private Integer id;
    private String title;
    
}