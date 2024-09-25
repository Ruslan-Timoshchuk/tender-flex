package pl.com.tenderflex.model;

import lombok.Builder;
import lombok.Data;
import pl.com.tenderflex.model.enums.ERole;

@Data
@Builder
public class Role {

    private Integer id;
    private ERole name;
    
}