package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import pl.com.tenderflex.model.Currency;
import pl.com.tenderflex.payload.response.CurrencyResponse;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyResponse currencyToCurrencyResponce(Currency currency);
    
}