package pl.com.tenderflex.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CurrencyResponse {

    private Integer id;
    private String currencyType;
    
}