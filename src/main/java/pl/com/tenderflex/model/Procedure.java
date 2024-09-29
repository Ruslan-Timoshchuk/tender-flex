package pl.com.tenderflex.model;

import lombok.Builder;
import lombok.Data;
import pl.com.tenderflex.model.enums.ELanguage;
import pl.com.tenderflex.model.enums.EProcedure;

@Data
@Builder
public class Procedure {

    private EProcedure type;
    private ELanguage language;
    
}