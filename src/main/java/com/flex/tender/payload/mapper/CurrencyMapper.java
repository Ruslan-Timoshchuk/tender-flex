package com.flex.tender.payload.mapper;

import org.mapstruct.Mapper;
import com.flex.tender.model.Currency;
import com.flex.tender.payload.request.CurrencyRequest;
import com.flex.tender.payload.response.CurrencyResponse;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    Currency toEntity(CurrencyRequest currencyRequest);
    
    CurrencyResponse teResponse(Currency currency);
    
}