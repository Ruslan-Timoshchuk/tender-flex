package pl.com.tenderflex.payload.mapstract;

import org.mapstruct.Mapper;
import pl.com.tenderflex.model.Currency;
import pl.com.tenderflex.payload.request.CurrencyRequest;
import pl.com.tenderflex.payload.response.CurrencyResponse;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    Currency toEntity(CurrencyRequest currencyRequest);
    
    CurrencyResponse teResponse(Currency currency);
    
}