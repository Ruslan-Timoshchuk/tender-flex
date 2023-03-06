package pl.com.tenderflex.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Currency {

    private Integer id;
    private String currencyType;
    
}