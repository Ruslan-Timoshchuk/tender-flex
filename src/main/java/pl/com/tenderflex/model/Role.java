package pl.com.tenderflex.model;

import com.flex.tender.model.enumeration.ERole;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Role {

    private Integer id;
    private ERole name;
    
}